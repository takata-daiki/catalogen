package com.wellsoft.pt.basicdata.printtemplate.service.impl;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Table;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.FieldsDocumentPart;
import org.apache.poi.hwpf.usermodel.Field;
import org.apache.poi.hwpf.usermodel.Fields;
import org.apache.poi.hwpf.usermodel.Range;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.wellsoft.pt.basicdata.printtemplate.bean.PrintTemplateBean;
import com.wellsoft.pt.basicdata.printtemplate.dao.PrintRecordDao;
import com.wellsoft.pt.basicdata.printtemplate.dao.PrintTemplateDao;
import com.wellsoft.pt.basicdata.printtemplate.entity.PrintRecord;
import com.wellsoft.pt.basicdata.printtemplate.entity.PrintTemplate;
import com.wellsoft.pt.basicdata.printtemplate.service.PrintTemplateService;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryData;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo;
import com.wellsoft.pt.common.enums.IdPrefix;
import com.wellsoft.pt.common.enums.Separator;
import com.wellsoft.pt.common.utils.DateUtil;
import com.wellsoft.pt.core.dao.Page;
import com.wellsoft.pt.core.entity.IdEntity;
import com.wellsoft.pt.core.resource.Config;
import com.wellsoft.pt.core.template.TemplateEngine;
import com.wellsoft.pt.core.template.TemplateEngineFactory;
import com.wellsoft.pt.org.entity.Department;
import com.wellsoft.pt.org.entity.User;
import com.wellsoft.pt.org.facade.OrgApiFacade;
import com.wellsoft.pt.repository.entity.mongo.file.MongoFileEntity;
import com.wellsoft.pt.repository.service.MongoFileService;
import com.wellsoft.pt.security.acl.entity.AclSid;
import com.wellsoft.pt.security.acl.service.AclService;
import com.wellsoft.pt.security.core.userdetails.UserDetails;
import com.wellsoft.pt.utils.bean.BeanUtils;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

import freemarker.template.Configuration;

/**
 * 
 * Description: 打印模板实现类
 *  
 * @author zhouyq
 * @date 2013-3-21
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-3-21.1	zhouyq		2013-3-21		Create
 * </pre>
 *
 */
@Service
@Transactional
public class PrintTemplateServiceImpl implements PrintTemplateService {

	@Autowired
	private PrintTemplateDao printTemplateDao;
	@Autowired
	private PrintRecordDao printRecordDao;

	@Autowired
	private AclService aclService;

	@Autowired
	private OrgApiFacade orgApiFacade;

	@Autowired
	private MongoFileService mongoFileService;

	private Configuration configuration = null;

	private Dispatch wordObject;

	/** 
	* 通过uuid查找打印模板
	* @see com.wellsoft.pt.message.service.PrintTemplateService#getById(java.lang.String)
	*/

	@Override
	public PrintTemplate getByUuid(String uuid) {
		return printTemplateDao.get(uuid);
	}

	/** 
		* 根据uuid查找对应的打印模板bean
		* @see com.wellsoft.pt.message.service.PrintTemplateService#getBeanById(java.lang.String)
		*/

	@Override
	public PrintTemplateBean getBeanByUuid(String uuid) {
		PrintTemplate printTemplate = this.printTemplateDao.get(uuid);
		PrintTemplateBean bean = new PrintTemplateBean();
		BeanUtils.copyProperties(printTemplate, bean);

		//设置打印模板使用人
		List<AclSid> aclSids = aclService.getSid(printTemplate);
		List<String> sids = new ArrayList<String>();
		for (AclSid sid : aclSids) {
			if (ACL_SID.equals(sid.getSid())) {
				continue;
			}
			sids.add(sid.getSid());
		}
		StringBuilder ownerIds = new StringBuilder();
		StringBuilder ownerNames = new StringBuilder();
		Iterator<String> it = sids.iterator();
		while (it.hasNext()) {
			String sid = it.next();
			if (sid.startsWith(IdPrefix.USER.getValue())) {
				User user = orgApiFacade.getUserById(sid);
				ownerIds.append(user.getId());
				ownerNames.append(user.getUserName());
			} else if (sid.startsWith(IdPrefix.ROLE.getValue())) {
				sid = sid.substring(IdPrefix.ROLE.getName().length() + 1);
				Department department = orgApiFacade.getDepartmentById(sid);
				ownerIds.append(department.getId());
				ownerNames.append(department.getName());
			}
			if (it.hasNext()) {
				ownerIds.append(Separator.SEMICOLON.getValue());
				ownerNames.append(Separator.SEMICOLON.getValue());
			}
		}
		bean.setOwnerIds(ownerIds.toString());
		bean.setOwnerNames(ownerNames.toString());
		return bean;
	}

	/** 
		* 保存打印模板bean
	 * @throws IOException 
	 * @see com.wellsoft.pt.message.service.PrintTemplateService#saveBean(com.wellsoft.pt.message.bean.PrintTemplateBean)
		*/

	@Override
	public PrintTemplate saveBean(PrintTemplateBean bean) throws IOException {
		PrintTemplate printTemplate = new PrintTemplate();
		// 保存新printTemplate 设置id值
		if (StringUtils.isBlank(bean.getUuid())) {
			bean.setUuid(null);
		} else {
			printTemplate = this.printTemplateDao.get(bean.getUuid());
		}
		BeanUtils.copyProperties(bean, printTemplate);

		//设置所有者
		if (StringUtils.isNotBlank(bean.getOwnerIds())) {
			String[] ownerIds = StringUtils.split(bean.getOwnerIds(), Separator.SEMICOLON.getValue());
			printTemplate.setOwners(Arrays.asList(ownerIds));
		}
		this.printTemplateDao.save(printTemplate);
		this.saveAcl(printTemplate);

		return printTemplate;

	}

	/** 
		* 删除打印模板
		* @see com.wellsoft.pt.message.service.PrintTemplateService#remove(java.lang.String)
		*/

	@Override
	public void remove(String uuid) {
		this.printTemplateDao.delete(uuid);
	}

	/** 
		* (non-Javadoc)
		* @see com.wellsoft.pt.message.service.PrintTemplateService#query(com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo)
		*/

	@Override
	public JqGridQueryData query(JqGridQueryInfo queryInfo) {
		Page<PrintTemplate> pageData = new Page<PrintTemplate>();
		pageData.setPageNo(queryInfo.getPage());
		pageData.setPageSize(queryInfo.getRows());
		this.printTemplateDao.findPage(pageData);
		List<PrintTemplate> printTemplates = pageData.getResult();
		List<PrintTemplate> jqUsers = new ArrayList<PrintTemplate>();
		for (PrintTemplate printTemplate : printTemplates) {
			PrintTemplate jqPrintTemplate = new PrintTemplate();
			BeanUtils.copyProperties(printTemplate, jqPrintTemplate);
			jqUsers.add(jqPrintTemplate);
		}
		JqGridQueryData queryData = new JqGridQueryData();
		queryData.setCurrentPage(queryInfo.getPage());
		queryData.setDataList(jqUsers);
		queryData.setRepeatitems(false);
		queryData.setTotalPages(pageData.getTotalPages());
		queryData.setTotalRows(pageData.getTotalCount());
		return queryData;
	}

	@Override
	public PrintTemplate saveAcl(PrintTemplate printTemplate) {
		List<AclSid> aclSids = aclService.getSid(printTemplate);
		List<String> existSids = new ArrayList<String>();
		for (AclSid aclSid : aclSids) {
			existSids.add(aclSid.getSid());
		}
		List<String> sids = getAclSid(printTemplate);
		//新的SID
		List<String> newSids = new ArrayList<String>();
		for (String newSid : sids) {
			if (!existSids.contains(newSid)) {
				newSids.add(newSid);
			}
		}
		//要删除的SID
		List<String> delSids = new ArrayList<String>();
		for (String newSid : existSids) {
			if (!sids.contains(newSid)) {
				delSids.add(newSid);
			}
		}

		//删除
		for (String sid : delSids) {
			aclService.removePermission(printTemplate, BasePermission.ADMINISTRATION, sid);
		}
		/*// 新增
		if (printTemplate.getParent() != null) {
			aclService.save(printTemplate, printTemplate.getParent(), sids.get(0), BasePermission.ADMINISTRATION);
		}*/
		for (String sid : sids) {
			aclService.addPermission(printTemplate, BasePermission.ADMINISTRATION, sid);
		}
		return aclService.get(PrintTemplate.class, printTemplate.getUuid(), BasePermission.ADMINISTRATION);
	}

	/**
		* 返回打印模板使用者在ACL中的SID
		* 
		* @param printTemplate
		* 
		* @return
		*/

	private List<String> getAclSid(PrintTemplate printTemplate) {
		List<String> newOwners = new ArrayList<String>();
		if (printTemplate.getOwners().isEmpty()) {
			printTemplate.getOwners().add(ACL_SID);
			return printTemplate.getOwners();
		} else {
			List<String> owners = printTemplate.getOwners();
			for (String owner : owners) {
				if (owner.startsWith(IdPrefix.DEPARTMENT.getValue())) {
					owner = "ROLE_" + owner;
				}
				newOwners.add(owner);
			}
		}
		// 返回组织部门中选择的角色作为SID
		return newOwners;
	}

	/**
		* 判断当前登录用户是否在指定的组织部门中
		* 
		* @param printTemplate
		* @param sid
		*/

	private Boolean hasPermission(PrintTemplate printTemplate) {
		Boolean hasPermission = false;
		// 获取该打印模板的所有SID，判断是否有访问权限
		List<AclSid> aclSids = aclService.getSid(printTemplate);
		for (AclSid aclSid : aclSids) {
			String sid = aclSid.getSid();
			// 如果所有者是默认的则有权限
			if (sid.equals(ACL_SID)) {
				hasPermission = true;
				break;
			} else {// 由组织部门提供接口，判断当前登录用户是否在指定的SID(组织部门)中
				if (sid.startsWith(IdPrefix.USER.getValue())) {
					if (StringUtils.equals(((UserDetails) SpringSecurityUtils.getCurrentUser()).getUserId(), sid)) {
						hasPermission = true;
						break;
					}
				} else {
					hasPermission = false;
				}
			}
		}
		return hasPermission;
	}

	/**
	 * 
	 * 打印模板调用接口,返回文件流（模板ID,单份工作,动态表单集合,输入文件(正文)）
	 * 
	 * @param templateId
	 * @param entities
	 */
	@Override
	public <ENTITY extends IdEntity> InputStream getPrintTemplateInputStream(String templateId,
			Collection<ENTITY> entities, Map<String, Object> dytableMap, File textFile) throws Exception {
		List<Collection<ENTITY>> allEntities = new ArrayList<Collection<ENTITY>>();
		allEntities.add(entities);
		List<Map<String, Object>> dytableMaps = new ArrayList<Map<String, Object>>();
		dytableMaps.add(dytableMap);
		File finalFile = getPrintTemplateFile(templateId, allEntities, dytableMaps, textFile);
		InputStream fileInStream = new FileInputStream(finalFile);
		return fileInStream;
	}

	/**
	 * 
	 * 打印模板调用接口,返回文件（模板ID,单份工作，动态表单集合，输入文件(正文)）
	 * 
	 * (non-Javadoc)
	 * @throws Exception 
	 * @see com.wellsoft.pt.basicdata.printtemplate.service.PrintTemplateService#getPrintTemplate(java.lang.String, java.util.Collection, java.io.InputStream)
	 */
	@Override
	public <ENTITY extends IdEntity> File getPrintTemplateFile(String templateId, Collection<ENTITY> entities,
			Map<String, Object> dytableMap, File textFile) throws Exception {
		List<Collection<ENTITY>> allEntities = new ArrayList<Collection<ENTITY>>();
		allEntities.add(entities);
		List<Map<String, Object>> dytableMaps = new ArrayList<Map<String, Object>>();
		dytableMaps.add(dytableMap);
		return getPrintTemplateFile(templateId, allEntities, dytableMaps, textFile);
	}

	/**
	 * 
	 * 打印模板调用接口,返回文件流（模板ID,多份工作,动态表单集合,输入文件(正文)）
	 * 
	 * @param templateId
	 * @param entities
	 */
	@Override
	public <ENTITY extends IdEntity> InputStream getPrintTemplateInputStream(String templateId,
			Collection<Collection<ENTITY>> allEntities, List<Map<String, Object>> dytableMaps, File textFile)
			throws Exception {
		File finalFile = getPrintTemplateFile(templateId, allEntities, dytableMaps, textFile);
		InputStream fileInStream = new FileInputStream(finalFile);
		return fileInStream;
	}

	/**
	 * 
	 * 打印模板调用接口,返回文件（模板ID,多份工作，动态表单集合，输入文件(正文)）
	 * 
	 * (non-Javadoc)
	 * @throws Exception 
	 * @see com.wellsoft.pt.basicdata.printtemplate.service.PrintTemplateService#getPrintTemplate(java.lang.String, java.util.Collection, java.io.InputStream)
	 */
	@Override
	public <ENTITY extends IdEntity> File getPrintTemplateFile(String templateId,
			Collection<Collection<ENTITY>> allEntities, List<Map<String, Object>> dytableMaps, File textFile)
			throws Exception {

		PrintTemplate printTemplate = printTemplateDao.findUniqueBy("id", templateId);
		File finalFile = null;
		//word模板类型
		if ("wordType".equals(printTemplate.getTemplateType())) {
			String uuid = printTemplate.getUuid();
			//下载该打印模板对应的模板文件
			List<MongoFileEntity> files = mongoFileService.getFilesFromFolder(uuid, "attach");
			MongoFileEntity mongoFileEntity = files.get(0);
			InputStream inputStreamOut = mongoFileEntity.getInputstream();
			//将文件输出流转为临时文件,使用"./"表示放在eclipseWorkspace\basic下
			FileUtils.copyInputStreamToFile(inputStreamOut, new File("./", "temp.doc"));
			System.out.println("临时文件输出成功！");

			//判断是否需要多次套打
			if (allEntities.size() == 1) {
				//判断是否保存到源文档
				if (printTemplate.getIsSaveSource()) {
					for (Collection<ENTITY> entities : allEntities) {
						//文件名定义格式
						String fileNameFormat = printTemplate.getFileNameFormat();
						fileNameFormat = fileNameFormat.replaceAll("}", "!}");//防止freemarker模板处理null出错
						Map<String, Object> templateMap = getMapValue(entities, dytableMaps.get(0));//获得模板集合
						TemplateEngine templateEngine = TemplateEngineFactory.getDefaultTemplateEngine();
						String fileName = templateEngine.process(fileNameFormat, templateMap);
						String outputPath = Config.HOME_DIR + "/" + fileName + ".doc";//文件输出路径
						Map<String, String> replaceMap = new HashMap<String, String>();//用来存放解析后的键值对
						File tempFile = getTempFile();//获取下载的临时文件
						String text = getWordText(tempFile);//获取临时word文件文本内容
						String reg = "\\$\\{.*?(?=\\}.*)";//文本内容中的关键字定义部分正则
						Pattern pattern = Pattern.compile(reg);
						Matcher matcher = pattern.matcher(text);
						String keyWords = "";
						while (matcher.find()) {
							//取得关键字
							String keyWord = matcher.group() + "!}";
							keyWords = keyWords + keyWord + ",";
							System.out.println("关键字：" + keyWord);
							//模板解析关键字
							String result = templateEngine.process(keyWord, templateMap);
							System.out.println("关键字替换后：" + result);
							String newKeyWord = keyWord.replaceAll("!", "");
							replaceMap.put(newKeyWord, result);
						}
						//读取关键字后保存在数据库中的关键字列
						String newKeyWords = keyWords.replaceAll("!", "");
						printTemplate.setKeyWords(newKeyWords);
						printTemplateDao.save(printTemplate);

						//完成关键字解析后对word进行操作
						finalFile = exportDocByMutiLine(tempFile.getCanonicalPath(), outputPath, replaceMap, textFile,
								printTemplate);
					}
				} else {
					//多个工作数量
					Integer docNumber = allEntities.size();
					Integer subScript = 0;
					//对多个工作进行遍历
					for (Collection<ENTITY> entities : allEntities) {
						Map<String, String> replaceMap = new HashMap<String, String>();//用来存放解析后的键值对
						Map<String, Object> templateMap = getMapValue(entities, dytableMaps.get(subScript));//获得模板集合
						subScript = subScript + 1;
						TemplateEngine templateEngine = TemplateEngineFactory.getDefaultTemplateEngine();
						File tempFile = getTempFile();//获取下载的临时文件
						String text = getWordText(tempFile);//获取临时word文件文本内容
						String reg = "\\$\\{.*?(?=\\}.*)";//文本内容中的关键字定义部分正则
						Pattern pattern = Pattern.compile(reg);
						Matcher matcher = pattern.matcher(text);
						String keyWords = "";
						while (matcher.find()) {
							//取得关键字
							String keyWord = matcher.group() + "!}";
							keyWords = keyWords + keyWord + ",";
							System.out.println("关键字：" + keyWord);
							//模板解析关键字
							String result = templateEngine.process(keyWord, templateMap);
							System.out.println("关键字替换后：" + result);
							String newKeyWord = keyWord.replaceAll("!", "");
							replaceMap.put(newKeyWord, result);
						}
						//读取关键字后保存在数据库中的关键字列
						String newKeyWords = keyWords.replaceAll("!", "");
						printTemplate.setKeyWords(newKeyWords);
						printTemplateDao.save(printTemplate);

						//完成关键字解析后对word进行操作
						exportDoc(tempFile.getCanonicalPath(), Config.HOME_DIR + "/" + docNumber + ".doc", replaceMap,
								textFile, printTemplate);
						docNumber = docNumber - 1;
					}

					//判断多次套打是分页还是多行
					if ("paging".equals(printTemplate.getPrintInterval())) {
						//合并word文档(分页)
						finalFile = uniteDocByPage(allEntities.size(), printTemplate);
					} else if ("multi_line".equals(printTemplate.getPrintInterval())) {
						//合并word文档(多行)
						finalFile = uniteDocByMultiLine(allEntities.size(), printTemplate);
					}
				}
			} else {
				//多个工作数量
				Integer docNumber = allEntities.size();
				Integer subScript = 0;
				//对多个工作进行遍历
				for (Collection<ENTITY> entities : allEntities) {
					Map<String, String> replaceMap = new HashMap<String, String>();//用来存放解析后的键值对
					Map<String, Object> templateMap = getMapValue(entities, dytableMaps.get(subScript));//获得模板集合
					subScript = subScript + 1;
					TemplateEngine templateEngine = TemplateEngineFactory.getDefaultTemplateEngine();
					File tempFile = getTempFile();//获取下载的临时文件
					String text = getWordText(tempFile);//获取临时word文件文本内容
					String reg = "\\$\\{.*?(?=\\}.*)";//文本内容中的关键字定义部分正则
					Pattern pattern = Pattern.compile(reg);
					Matcher matcher = pattern.matcher(text);
					String keyWords = "";
					while (matcher.find()) {
						//取得关键字
						String keyWord = matcher.group() + "!}";
						keyWords = keyWords + keyWord + ",";
						System.out.println("关键字：" + keyWord);
						//模板解析关键字
						String result = templateEngine.process(keyWord, templateMap);
						System.out.println("关键字替换后：" + result);
						String newKeyWord = keyWord.replaceAll("!", "");
						replaceMap.put(newKeyWord, result);
					}
					//读取关键字后保存在数据库中的关键字列
					String newKeyWords = keyWords.replaceAll("!", "");
					printTemplate.setKeyWords(newKeyWords);
					printTemplateDao.save(printTemplate);

					//完成关键字解析后对word进行操作
					exportDoc(tempFile.getCanonicalPath(), Config.HOME_DIR + "/" + docNumber + ".doc", replaceMap,
							textFile, printTemplate);
					docNumber = docNumber - 1;
				}

				//判断多次套打是分页还是多行
				if ("paging".equals(printTemplate.getPrintInterval())) {
					//合并word文档(分页)
					finalFile = uniteDocByPage(allEntities.size(), printTemplate);
				} else if ("multi_line".equals(printTemplate.getPrintInterval())) {
					//合并word文档(多行)
					finalFile = uniteDocByMultiLine(allEntities.size(), printTemplate);
				}
			}

			//判断是否保存打印记录
			if (printTemplate.getIsSavePrintRecord()) {
				for (Collection<ENTITY> entities : allEntities) {
					PrintRecord printRecord = new PrintRecord();
					String printObjectUuid = "";
					String printObjectType = "";
					for (ENTITY entity : entities) {
						printObjectUuid = printObjectUuid + entity.getUuid() + ",";
						printObjectType = printObjectType + entity.getClass().getName() + ",";
					}
					//打印记录历史次数集合
					List<Integer> oldPrintTimes = new ArrayList<Integer>();
					List<PrintRecord> oldPrintRecords = printRecordDao.findBy("printObject", printObjectUuid);
					for (PrintRecord oldPrintRecord : oldPrintRecords) {
						Integer oldPrintTimes1 = oldPrintRecord.getPrintTimes();
						oldPrintTimes.add(oldPrintTimes1);
					}
					//取得打印次数的最大值
					int maxOldPrintTimes = 0;
					for (int i = 0; i < oldPrintTimes.size(); i++) {
						if (oldPrintTimes.get(i) > maxOldPrintTimes) {
							maxOldPrintTimes = oldPrintTimes.get(i);
						}
					}
					//如果打印记录不为null的话
					if (oldPrintRecords != null) {
						printRecord.setPrintTimes(maxOldPrintTimes + 1);
					} else {
						printRecord.setPrintTimes(1);
					}
					printRecord.setPrintObject(printObjectUuid);
					printRecord.setPrintObjectType(printObjectType);
					//获取调用人
					String userName = SpringSecurityUtils.getCurrentUserName();
					System.out.println("当前用户名：" + userName);
					printRecord.setUserName(userName);
					printRecord.setCode(templateId);
					printRecordDao.save(printRecord);
				}
			}
		} else if ("htmlType".equals(printTemplate.getTemplateType())) {
			//判断是否需要多次套打
			if (allEntities.size() == 1) {
				//判断是否保存到源文档
				if (printTemplate.getIsSaveSource()) {
					for (Collection<ENTITY> entities : allEntities) {
						//文件流每次被读取的话都要新下载该文件流
						String uuid = printTemplate.getUuid();
						//下载该打印模板对应的html模板文件
						List<MongoFileEntity> files = mongoFileService.getFilesFromFolder(uuid, "attach");
						MongoFileEntity mongoFileEntity = files.get(0);
						InputStream inputStreamOut = mongoFileEntity.getInputstream();
						//文件名定义格式
						String fileNameFormat = printTemplate.getFileNameFormat();
						fileNameFormat = fileNameFormat.replaceAll("}", "!}");//防止freemarker模板处理null出错
						Map<String, Object> templateMap = getMapValue(entities, dytableMaps.get(0));//获得模板集合
						TemplateEngine templateEngine = TemplateEngineFactory.getDefaultTemplateEngine();
						String fileName = templateEngine.process(fileNameFormat, templateMap);
						String outputPath = Config.HOME_DIR + "/" + fileName + ".doc";//文件输出路径
						Map<String, String> replaceMap = new HashMap<String, String>();//用来存放解析后的键值对

						//读取html模板流并替换为freemarker模板数据内容
						String beforeReplaceHtml = IOUtils.toString(inputStreamOut);
						String afterReplaceHtml = templateEngine.process(beforeReplaceHtml, templateMap);

						InputStream afterReplaceInputStream = new ByteArrayInputStream(
								afterReplaceHtml.getBytes("UTF-8"));//html模板替换后的文件流
						//						File tempHtmlFile = new File("./", "tempHtml.doc");
						FileUtils.copyInputStreamToFile(afterReplaceInputStream, new File(outputPath));
						finalFile = new File(outputPath);

						/*	//如果套打模板设置为只读，则最终的结果以pdf来展示
							if (printTemplate.getIsReadOnly()) {
								File afterReplaceHtmlToWord = new File("./", "tempHtmlToWord.doc");
								PrinttemplateUtil.htmlToWord(finalFile.getCanonicalPath(),
										afterReplaceHtmlToWord.getCanonicalPath());
								//删除临时html文件流转成的word文档tempHtml.doc
								finalFile.delete();
								//html转为word之后样式会有变化，所以这时要对页面进行设置（纸张大小、页边距等）
								ActiveXComponent word = PrinttemplateUtil.getActiveXComponent();
								Dispatch wordDispatchDocument = PrinttemplateUtil.getWordDispatchDocument(word);
								Dispatch currentDispatch = PrinttemplateUtil.open(wordDispatchDocument,
										afterReplaceHtmlToWord.getCanonicalPath());
								PrinttemplateUtil.setPageSetup(currentDispatch, 1, 8, 45, 45, 40, 40);
								PrinttemplateUtil.save(word, afterReplaceHtmlToWord.getCanonicalPath());
								PrinttemplateUtil.closeActiveXComponent(word);

								fileService.convert2PDF(FileUtils.openInputStream(afterReplaceHtmlToWord),
										printTemplate.getUuid());
								//删除临时word文档tempHtmlToWord.doc
								afterReplaceHtmlToWord.delete();
								finalFile = new File(Config.APP_DATA_DIR + "/pdf2swf/" + printTemplate.getUuid() + ".pdf");
							}*/
					}
				} else {
					//多个工作数量
					Integer docNumber = allEntities.size();
					Integer subScript = 0;
					//对多个工作进行遍历
					for (Collection<ENTITY> entities : allEntities) {
						//文件流每次被读取的话都要新下载该文件流
						String uuid = printTemplate.getUuid();
						//下载该打印模板对应的html模板文件
						List<MongoFileEntity> files = mongoFileService.getFilesFromFolder(uuid, "attach");
						MongoFileEntity mongoFileEntity = files.get(0);
						InputStream inputStreamOut = mongoFileEntity.getInputstream();
						Map<String, String> replaceMap = new HashMap<String, String>();//用来存放解析后的键值对
						Map<String, Object> templateMap = getMapValue(entities, dytableMaps.get(subScript));//获得模板集合
						subScript = subScript + 1;
						TemplateEngine templateEngine = TemplateEngineFactory.getDefaultTemplateEngine();

						//读取html模板流并替换为freemarker模板数据内容
						String beforeReplaceHtml = IOUtils.toString(inputStreamOut);
						String afterReplaceHtml = templateEngine.process(beforeReplaceHtml, templateMap);

						InputStream afterReplaceInputStream = new ByteArrayInputStream(
								afterReplaceHtml.getBytes("UTF-8"));//html模板替换后的文件流
						//						File tempHtmlFile = new File(Config.HOME_DIR + "/" + docNumber + ".doc");//多份工作临时存放位置，存放格式为1.doc,2.doc……
						String outputPath = Config.HOME_DIR + "/" + getFileName() + ".doc";//文件输出路径
						FileUtils.copyInputStreamToFile(afterReplaceInputStream, new File(outputPath));
						finalFile = new File(outputPath);
						docNumber = docNumber - 1;
					}

					/*//判断多次套打是分页还是多行
					if ("paging".equals(printTemplate.getPrintInterval())) {
						//合并word文档(分页)
						finalFile = uniteDocByPage(allEntities.size(), printTemplate);
					} else if ("multi_line".equals(printTemplate.getPrintInterval())) {
						//合并word文档(多行)
						finalFile = uniteDocByMultiLine(allEntities.size(), printTemplate);
					}
					*/
					/*//如果套打模板设置为只读，则最终的结果以pdf来展示
					if (printTemplate.getIsReadOnly()) {
						File afterReplaceHtmlToWord = new File("./", "tempHtmlToWord.doc");
						PrinttemplateUtil.htmlToWord(finalFile.getCanonicalPath(),
								afterReplaceHtmlToWord.getCanonicalPath());
						//删除临时html文件流转成的word文档tempHtml.doc
						finalFile.delete();
						//html转为word之后样式会有变化，所以这时要对页面进行设置（纸张大小、页边距等）
						ActiveXComponent word = PrinttemplateUtil.getActiveXComponent();
						Dispatch wordDispatchDocument = PrinttemplateUtil.getWordDispatchDocument(word);
						Dispatch currentDispatch = PrinttemplateUtil.open(wordDispatchDocument,
								afterReplaceHtmlToWord.getCanonicalPath());
						PrinttemplateUtil.setPageSetup(currentDispatch, 1, 8, 45, 45, 40, 40);
						PrinttemplateUtil.save(word, afterReplaceHtmlToWord.getCanonicalPath());
						PrinttemplateUtil.closeActiveXComponent(word);

						fileService.convert2PDF(FileUtils.openInputStream(afterReplaceHtmlToWord),
								printTemplate.getUuid());
						//删除临时word文档tempHtmlToWord.doc
						afterReplaceHtmlToWord.delete();
						finalFile = new File(Config.APP_DATA_DIR + "/pdf2swf/" + printTemplate.getUuid() + ".pdf");
					}*/
				}
			} else {
				//多个工作数量
				Integer docNumber = allEntities.size();
				Integer subScript = 0;
				//对多个工作进行遍历
				for (Collection<ENTITY> entities : allEntities) {
					//文件流每次被读取的话都要新下载该文件流
					String uuid = printTemplate.getUuid();
					//下载该打印模板对应的html模板文件
					List<MongoFileEntity> files = mongoFileService.getFilesFromFolder(uuid, "attach");
					MongoFileEntity mongoFileEntity = files.get(0);
					InputStream inputStreamOut = mongoFileEntity.getInputstream();
					Map<String, String> replaceMap = new HashMap<String, String>();//用来存放解析后的键值对
					Map<String, Object> templateMap = getMapValue(entities, dytableMaps.get(subScript));//获得模板集合
					subScript = subScript + 1;
					TemplateEngine templateEngine = TemplateEngineFactory.getDefaultTemplateEngine();

					//读取html模板流并替换为freemarker模板数据内容
					String beforeReplaceHtml = IOUtils.toString(inputStreamOut);
					String afterReplaceHtml = templateEngine.process(beforeReplaceHtml, templateMap);

					InputStream afterReplaceInputStream = new ByteArrayInputStream(afterReplaceHtml.getBytes("UTF-8"));//html模板替换后的文件流
					//					File tempHtmlFile = new File(Config.HOME_DIR + "/" + docNumber + ".doc");//多份工作临时存放位置，存放格式为1.doc,2.doc……
					String outputPath = Config.HOME_DIR + "/" + getFileName() + ".doc";//文件输出路径
					FileUtils.copyInputStreamToFile(afterReplaceInputStream, new File(outputPath));
					finalFile = new File(outputPath);

					docNumber = docNumber - 1;
				}

				/*//判断多次套打是分页还是多行
				if ("paging".equals(printTemplate.getPrintInterval())) {
					//合并word文档(分页)
					finalFile = uniteDocByPage(allEntities.size(), printTemplate);
				} else if ("multi_line".equals(printTemplate.getPrintInterval())) {
					//合并word文档(多行)
					finalFile = uniteDocByMultiLine(allEntities.size(), printTemplate);
				}

				//如果套打模板设置为只读，则最终的结果以pdf来展示
				if (printTemplate.getIsReadOnly()) {
					File afterReplaceHtmlToWord = new File("./", "tempHtmlToWord.doc");
					PrinttemplateUtil.htmlToWord(finalFile.getCanonicalPath(),
							afterReplaceHtmlToWord.getCanonicalPath());
					//删除临时html文件流转成的word文档tempHtml.doc
					finalFile.delete();
					//html转为word之后样式会有变化，所以这时要对页面进行设置（纸张大小、页边距等）
					ActiveXComponent word = PrinttemplateUtil.getActiveXComponent();
					Dispatch wordDispatchDocument = PrinttemplateUtil.getWordDispatchDocument(word);
					Dispatch currentDispatch = PrinttemplateUtil.open(wordDispatchDocument,
							afterReplaceHtmlToWord.getCanonicalPath());
					PrinttemplateUtil.setPageSetup(currentDispatch, 1, 8, 45, 45, 40, 40);
					PrinttemplateUtil.save(word, afterReplaceHtmlToWord.getCanonicalPath());
					PrinttemplateUtil.closeActiveXComponent(word);

					fileService.convert2PDF(FileUtils.openInputStream(afterReplaceHtmlToWord), printTemplate.getUuid());
					//删除临时word文档tempHtmlToWord.doc
					afterReplaceHtmlToWord.delete();
					finalFile = new File(Config.APP_DATA_DIR + "/pdf2swf/" + printTemplate.getUuid() + ".pdf");
				}*/
			}

			//判断是否保存打印记录
			if (printTemplate.getIsSavePrintRecord()) {
				for (Collection<ENTITY> entities : allEntities) {
					PrintRecord printRecord = new PrintRecord();
					String printObjectUuid = "";
					String printObjectType = "";
					for (ENTITY entity : entities) {
						printObjectUuid = printObjectUuid + entity.getUuid() + ",";
						printObjectType = printObjectType + entity.getClass().getName() + ",";
					}
					//打印记录历史次数集合
					List<Integer> oldPrintTimes = new ArrayList<Integer>();
					List<PrintRecord> oldPrintRecords = printRecordDao.findBy("printObject", printObjectUuid);
					for (PrintRecord oldPrintRecord : oldPrintRecords) {
						Integer oldPrintTimes1 = oldPrintRecord.getPrintTimes();
						oldPrintTimes.add(oldPrintTimes1);
					}
					//取得打印次数的最大值
					int maxOldPrintTimes = 0;
					for (int i = 0; i < oldPrintTimes.size(); i++) {
						if (oldPrintTimes.get(i) > maxOldPrintTimes) {
							maxOldPrintTimes = oldPrintTimes.get(i);
						}
					}
					//如果打印记录不为null的话
					if (oldPrintRecords != null) {
						printRecord.setPrintTimes(maxOldPrintTimes + 1);
					} else {
						printRecord.setPrintTimes(1);
					}
					printRecord.setPrintObject(printObjectUuid);
					printRecord.setPrintObjectType(printObjectType);
					//获取调用人
					String userName = SpringSecurityUtils.getCurrentUserName();
					System.out.println("当前用户名：" + userName);
					printRecord.setUserName(userName);
					printRecord.setCode(templateId);
					printRecordDao.save(printRecord);
				}
			}
		}
		return finalFile;
	}

	/**
	 * 
	 * 合并文件（多行）
	 *
	 */
	private <ENTITY extends IdEntity> File uniteDocByMultiLine(Integer docNumber, PrintTemplate printTemplate) {
		Integer rows = printTemplate.getRowNumber();//获取行数
		String outputPath = Config.HOME_DIR + "/" + getFileName() + ".doc";//合并文件输出路径
		System.out.println("合并文件输出路径:" + outputPath);
		List<String> fileList = new ArrayList<String>();
		for (int i = 1; i <= docNumber; i++) {
			String file = Config.HOME_DIR + "/" + i + ".doc";
			fileList.add(file);
			System.out.println("当前文件名为：" + file);
		}

		if (fileList.size() == 0 || fileList == null) {
			return null;
		}
		//打开word
		ActiveXComponent active = new ActiveXComponent("Word.Application");//启动word
		try {
			// 设置word不可见
			active.setProperty("Visible", new Variant(false));
			//获得documents对象
			Object docs = active.getProperty("Documents").toDispatch();
			//打开第一个文件
			Dispatch doc = Dispatch.invoke((Dispatch) docs, "Open", Dispatch.Method,
					new Object[] { (String) fileList.get(0), new Variant(false), new Variant(true) }, new int[3])
					.toDispatch();
			//选择区域
			Dispatch selection = this.select(active);
			//判断文本内容中是否包含{BOTTOM}
			if (getWordText(getTempFile()).contains("{BOTTOM}")) {
				//置底部分不用显示痕迹
				Dispatch.put(doc, "TrackRevisions", new Variant(false));
				Dispatch.put(doc, "PrintRevisions", new Variant(false));
				Dispatch.put(doc, "ShowRevisions", new Variant(false));

				System.out.println("文本中包含{BOTTOM},需要置底");
				moveStart(selection);
				Boolean result = find("{BOTTOM}", selection, active);//查找{BOTTOM}关键字并选中
				if (result) {
					//替换{BOTTOM}为空
					this.replaceAll(selection, "{BOTTOM}", "");
					System.out.println("{BOTTOM}已经替换为空");
					//				// 插入分页符  
					//				Dispatch.call(selection, "InsertBreak", new Variant(7));
					//				moveUp(2, selection);//将光标往上移动两行
					int pages1 = Integer.parseInt(Dispatch.call(selection, "information", 4).toString());//总页数 //显示修订内容的最终状态
					System.out.println("移动前总页数：" + pages1);
					boolean flag = false;
					while (pages1 == Integer.parseInt(Dispatch.call(selection, "information", 4).toString()) && !flag) {
						insertBlank(selection);//插入空白行
						while (Integer.parseInt(Dispatch.call(selection, "information", 4).toString()) > pages1) {
							moveUp(1, selection, active);//超过当前页光标上移
							//						goToEnd(selection);//光标移动至末尾
							Dispatch.call(selection, "Delete");//删除当前空行
							flag = true;
							break;
						}
					}
				}
			}
			//追加文件
			for (int i = 1; i < fileList.size(); i++) {
				goToEnd(selection);
				if (i == 1) {
					for (int j = 0; j <= rows; j++) {
						insertBlank(selection);
					}
				} else {
					//第二份文件开始追加时少一个空行
					for (int j = 0; j <= rows - 1; j++) {
						insertBlank(selection);
					}
				}

				Dispatch.invoke(active.getProperty("Selection").toDispatch(), "insertFile", Dispatch.Method,
						new Object[] { (String) fileList.get(i), "", new Variant(false), new Variant(false),
								new Variant(false) }, new int[3]);
				//判断文本内容中是否包含{BOTTOM}
				if (getWordText(getTempFile()).contains("{BOTTOM}")) {
					//置底部分不用显示痕迹
					Dispatch.put(doc, "TrackRevisions", new Variant(false));
					Dispatch.put(doc, "PrintRevisions", new Variant(false));
					Dispatch.put(doc, "ShowRevisions", new Variant(false));

					System.out.println("文本中包含{BOTTOM},需要置底");
					moveStart(selection);
					Boolean result = find("{BOTTOM}", selection, active);//查找{BOTTOM}关键字并选中
					if (result) {
						//替换{BOTTOM}为空
						this.replaceAll(selection, "{BOTTOM}", "");
						System.out.println("{BOTTOM}已经替换为空");
						//				// 插入分页符  
						//				Dispatch.call(selection, "InsertBreak", new Variant(7));
						//				moveUp(2, selection);//将光标往上移动两行
						int pages1 = Integer.parseInt(Dispatch.call(selection, "information", 4).toString());//总页数 //显示修订内容的最终状态
						System.out.println("移动前总页数：" + pages1);
						boolean flag = false;
						while (pages1 == Integer.parseInt(Dispatch.call(selection, "information", 4).toString())
								&& !flag) {
							insertBlank(selection);//插入空白行
							while (Integer.parseInt(Dispatch.call(selection, "information", 4).toString()) > pages1) {
								flag = true;
								break;
							}
						}
					}
				}
			}
			//删除最后一页空白页
			goToEnd(selection);//光标移动至末尾
			Dispatch.call(selection, "Delete");//删除当前空行
			//保存新的word文件
			Dispatch.invoke((Dispatch) doc, "SaveAs", Dispatch.Method, new Object[] { outputPath, new Variant(1) },
					new int[3]);
			if (printTemplate.getIsReadOnly()) {
				new File(outputPath).setWritable(false);//将输出文档设为只读
			}
			Variant f = new Variant(false);
			Dispatch.call((Dispatch) doc, "Close", f);
			//合并完毕后删除原来文件
			for (int i = 1; i <= docNumber; i++) {
				String file = Config.HOME_DIR + "/" + i + ".doc";
				File delFile = new File(file);
				delFile.delete();
				System.out.println("原来文件删除成功！");
			}
			//删除临时文件temp.doc
			new File("./", "temp.doc").delete();
			System.out.println("临时文件删除成功！");
		} catch (Exception e) {
			throw new RuntimeException("合并word文件出错.原因:" + e);
		} finally {
			active.invoke("Quit", new Variant[] {});
		}
		System.out.println("多行合并成功！");
		return new File(outputPath);
	}

	/**
	 * 
	 * 合并文件（分页）
	 *
	 */
	public <ENTITY extends IdEntity> File uniteDocByPage(Integer docNumber, PrintTemplate printTemplate) {
		String outputPath = Config.HOME_DIR + "/" + getFileName() + ".doc";//合并文件输出路径
		System.out.println("合并文件输出路径:" + outputPath);
		List<String> fileList = new ArrayList<String>();
		for (int i = 1; i <= docNumber; i++) {
			String file = Config.HOME_DIR + "/" + i + ".doc";
			fileList.add(file);
			System.out.println("当前文件名为：" + file);
		}
		if (fileList.size() == 0 || fileList == null) {
			return null;
		}
		//打开word
		ActiveXComponent active = new ActiveXComponent("Word.Application");//启动word
		try {
			// 设置word不可见
			active.setProperty("Visible", new Variant(false));
			//获得documents对象
			Object docs = active.getProperty("Documents").toDispatch();
			//打开第一个文件
			Object doc = Dispatch.invoke((Dispatch) docs, "Open", Dispatch.Method,
					new Object[] { (String) fileList.get(0), new Variant(false), new Variant(true) }, new int[3])
					.toDispatch();
			//选择区域
			Dispatch selection = this.select(active);
			//追加文件
			for (int i = 1; i < fileList.size(); i++) {
				goToEnd(selection);//光标移动至末尾
				PageBreak(selection);//插入分页符
				Dispatch.invoke(active.getProperty("Selection").toDispatch(), "insertFile", Dispatch.Method,
						new Object[] { (String) fileList.get(i), "", new Variant(false), new Variant(false),
								new Variant(false) }, new int[3]);
			}
			//删除最后一页空白页
			goToEnd(selection);//光标移动至末尾
			Dispatch.call(selection, "Delete");//删除当前空行
			//保存新的word文件
			Dispatch.invoke((Dispatch) doc, "SaveAs", Dispatch.Method, new Object[] { outputPath, new Variant(1) },
					new int[3]);
			if (printTemplate.getIsReadOnly()) {
				new File(outputPath).setWritable(false);//将输出文档设为只读
			}
			Variant f = new Variant(false);
			Dispatch.call((Dispatch) doc, "Close", f);
			//合并完毕后删除原来文件
			for (int i = 1; i <= docNumber; i++) {
				String file = Config.HOME_DIR + "/" + i + ".doc";
				File delFile = new File(file);
				delFile.delete();
				System.out.println("原来文件删除成功！");
			}
			//删除临时文件temp.doc
			new File("./", "temp.doc").delete();
			System.out.println("临时文件删除成功！");
		} catch (Exception e) {
			throw new RuntimeException("合并word文件出错.原因:" + e);
		} finally {
			active.invoke("Quit", new Variant[] {});
		}
		System.out.println("分页合并成功！");
		return new File(outputPath);
	}

	/**
	 * 解析关键字后操作word(单工作多行)
	 * @param inputDocPath
	 * @param outPutDocPath
	 * @param map
	 * @param isPrint
	 * @throws IOException 
	 */
	public File exportDocByMutiLine(String inputDocPath, String outPutDocPath, Map<String, String> map, File textFile,
			PrintTemplate printTemplate) {
		//初始化com的线程
		ComThread.InitSTA();
		//word运行程序对象
		ActiveXComponent word = new ActiveXComponent("Word.Application");
		//文档对象
		Dispatch wordObject = (Dispatch) word.getObject();
		//设置属性  Variant(true)表示word应用程序可见
		Dispatch.put((Dispatch) wordObject, "Visible", new Variant(false));
		//word所有文档
		Dispatch documents = word.getProperty("Documents").toDispatch();
		//打开模板文档
		Dispatch document = this.open(documents, inputDocPath);
		//选择区域
		Dispatch selection = this.select(word);

		Iterator keys = map.keySet().iterator();
		String oldText;
		Object newValue;
		while (keys.hasNext()) {
			oldText = (String) keys.next();//替换前关键字
			newValue = map.get(oldText);//替换后的关键字
			this.replaceAll(selection, oldText, newValue);
		}
		//判断是否包含{正文}及正文内容的文件
		if (getWordText(getTempFile()).contains("{正文}") && textFile.exists()) {
			//判断是否需要保留正文修改痕迹
			if (printTemplate.getIsSaveTrace()) {
				Dispatch.put(document, "TrackRevisions", new Variant(true));
				Dispatch.put(document, "PrintRevisions", new Variant(true));
				Dispatch.put(document, "ShowRevisions", new Variant(true));
			}

			//打开含正文内容文档
			Dispatch textDocument;
			try {
				textDocument = this.open(documents, textFile.getCanonicalPath());
				Dispatch textSelection = this.select(word);
				Dispatch wordContent = Dispatch.get(textDocument, "Content").toDispatch(); //取得正文word文件的内容
				Dispatch.call(wordContent, "Select");//全选
				Dispatch.call(textSelection, "Copy");//复制
				this.close(textDocument);//关闭正文word
				//替换{正文}为空
				this.replaceAll(selection, "{正文}", "");
				System.out.println("{正文}已经替换为空");
				paste(selection);//粘贴至源文档
			} catch (IOException e) {
				e.printStackTrace();
				this.close(document);//出异常则关闭当前word
				word.invoke("Quit", new Variant[0]);
			}
		}
		//判断文本内容中是否包含{BOTTOM}
		if (getWordText(getTempFile()).contains("{BOTTOM}")) {
			//置底部分不用显示痕迹
			Dispatch.put(document, "TrackRevisions", new Variant(false));
			Dispatch.put(document, "PrintRevisions", new Variant(false));
			Dispatch.put(document, "ShowRevisions", new Variant(false));

			System.out.println("文本中包含{BOTTOM},需要置底");
			moveStart(selection);
			Boolean result = find("{BOTTOM}", selection, word);//查找{BOTTOM}关键字并选中
			if (result) {
				//替换{BOTTOM}为空
				this.replaceAll(selection, "{BOTTOM}", "");
				System.out.println("{BOTTOM}已经替换为空");
				//				// 插入分页符  
				//				Dispatch.call(selection, "InsertBreak", new Variant(7));
				//				moveUp(2, selection);//将光标往上移动两行
				int pages1 = Integer.parseInt(Dispatch.call(selection, "information", 4).toString());//总页数 //显示修订内容的最终状态
				System.out.println("移动前总页数：" + pages1);
				boolean flag = false;
				while (pages1 == Integer.parseInt(Dispatch.call(selection, "information", 4).toString()) && !flag) {
					insertBlank(selection);//插入空白行
					while (Integer.parseInt(Dispatch.call(selection, "information", 4).toString()) > pages1) {
						moveUp(1, selection, word);//超过当前页光标上移
						//						goToEnd(selection);//光标移动至末尾
						Dispatch.call(selection, "Delete");//删除当前空行
						flag = true;
						break;
					}
				}
			}
		}

		this.save(word, outPutDocPath, printTemplate, document);
		this.close(document);
		word.invoke("Quit", new Variant[0]);
		//关闭com的线程
		ComThread.Release();
		return new File(outPutDocPath);
	}

	/**
	 * 解析关键字后操作word
	 * @param inputDocPath
	 * @param outPutDocPath
	 * @param map
	 * @param isPrint
	 * @throws IOException 
	 */
	public File exportDoc(String inputDocPath, String outPutDocPath, Map<String, String> map, File textFile,
			PrintTemplate printTemplate) {
		//初始化com的线程
		ComThread.InitSTA();
		//word运行程序对象
		ActiveXComponent word = new ActiveXComponent("Word.Application");
		//文档对象
		Dispatch wordObject = (Dispatch) word.getObject();
		//设置属性  Variant(true)表示word应用程序可见
		Dispatch.put((Dispatch) wordObject, "Visible", new Variant(false));
		//word所有文档
		Dispatch documents = word.getProperty("Documents").toDispatch();
		//打开模板文档
		Dispatch document = this.open(documents, inputDocPath);
		//选择区域
		Dispatch selection = this.select(word);

		Iterator keys = map.keySet().iterator();
		String oldText;
		Object newValue;
		while (keys.hasNext()) {
			oldText = (String) keys.next();//替换前关键字
			newValue = map.get(oldText);//替换后的关键字
			this.replaceAll(selection, oldText, newValue);
		}
		//判断是否包含{正文}及正文内容的文件
		if (getWordText(getTempFile()).contains("{正文}") && textFile.exists()) {
			//判断是否需要保留正文修改痕迹
			if (printTemplate.getIsSaveTrace()) {
				Dispatch.put(document, "TrackRevisions", new Variant(true));
				Dispatch.put(document, "PrintRevisions", new Variant(true));
				Dispatch.put(document, "ShowRevisions", new Variant(true));
			}

			//打开含正文内容文档
			Dispatch textDocument;
			try {
				textDocument = this.open(documents, textFile.getCanonicalPath());
				Dispatch textSelection = this.select(word);
				Dispatch wordContent = Dispatch.get(textDocument, "Content").toDispatch(); //取得正文word文件的内容
				Dispatch.call(wordContent, "Select");//全选
				Dispatch.call(textSelection, "Copy");//复制
				this.close(textDocument);//关闭正文word
				//替换{正文}为空
				this.replaceAll(selection, "{正文}", "");
				System.out.println("{正文}已经替换为空");
				paste(selection);//粘贴至源文档
			} catch (IOException e) {
				e.printStackTrace();
				this.close(document);//出异常则关闭当前word
				word.invoke("Quit", new Variant[0]);
			}
		}
		//如果是分页先执行置底然后再合并
		if ("paging".equals(printTemplate.getPrintInterval())) {
			//判断文本内容中是否包含{BOTTOM}
			if (getWordText(getTempFile()).contains("{BOTTOM}")) {
				//置底部分不用显示痕迹
				Dispatch.put(document, "TrackRevisions", new Variant(false));
				Dispatch.put(document, "PrintRevisions", new Variant(false));
				Dispatch.put(document, "ShowRevisions", new Variant(false));

				System.out.println("文本中包含{BOTTOM},需要置底");
				moveStart(selection);
				Boolean result = find("{BOTTOM}", selection, word);//查找{BOTTOM}关键字并选中
				if (result) {
					//替换{BOTTOM}为空
					this.replaceAll(selection, "{BOTTOM}", "");
					System.out.println("{BOTTOM}已经替换为空");
					//				// 插入分页符  
					//				Dispatch.call(selection, "InsertBreak", new Variant(7));
					//				moveUp(2, selection);//将光标往上移动两行
					int pages1 = Integer.parseInt(Dispatch.call(selection, "information", 4).toString());//总页数 //显示修订内容的最终状态
					System.out.println("移动前总页数：" + pages1);
					boolean flag = false;
					while (pages1 == Integer.parseInt(Dispatch.call(selection, "information", 4).toString()) && !flag) {
						insertBlank(selection);//插入空白行
						while (Integer.parseInt(Dispatch.call(selection, "information", 4).toString()) > pages1) {
							moveUp(1, selection, word);//超过当前页光标上移
							//						goToEnd(selection);//光标移动至末尾
							Dispatch.call(selection, "Delete");//删除当前空行
							flag = true;
							break;
						}
					}
				}
			}
		}

		this.save(word, outPutDocPath, printTemplate, document);
		this.close(document);
		word.invoke("Quit", new Variant[0]);
		//关闭com的线程
		ComThread.Release();
		return new File(outPutDocPath);
	}

	/**
	 * 
	 * 插入空行
	 * 
	 * @param selection
	 */
	public void insertBlank(Dispatch selection) {
		Dispatch.call(selection, "TypeParagraph");//插入空行
	}

	/**
	 * 插入分页符
	 * 
	 * @param selection
	 */
	public void PageBreak(Dispatch selection) {
		Dispatch.call(selection, "InsertBreak", new Variant(7));
	}

	/**
	 * 按下Ctrl + End键
	 */
	public void goToEnd(Dispatch selection) {
		Dispatch.call(selection, "EndKey", "6");
	}

	/**
	 * 执行某条宏指令
	 * 
	 * @param cmd
	 */
	private void cmd(String cmd, Dispatch selection) {
		Dispatch.call(selection, cmd);
	}

	/**
	* 在当前光标处做粘贴
	*/
	public void paste(Dispatch selection) {
		Dispatch.call(selection, "Paste");
	}

	/**
	* 把选定的内容或插入点向上移动
	* 
	* @param pos
	*            移动的距离
	*/

	public void moveUp(int pos, Dispatch selection, ActiveXComponent word) {
		if (selection == null)
			selection = Dispatch.get(word, "Selection").toDispatch();
		for (int i = 0; i < pos; i++)
			Dispatch.call(selection, "MoveUp");
	}

	/**
	 * 把选定的内容或者插入点向下移动
	 * 
	 * @param pos
	 *            移动的距离
	 */

	public void moveDown(Dispatch selection, ActiveXComponent word) {
		if (selection == null)
			selection = Dispatch.get(word, "Selection").toDispatch();
		//		for (int i = 0; i < pos; i++)
		Dispatch.call(selection, "MoveDown");
	}

	/**
	 * 从选定内容或插入点开始查找文本
	 * 
	 * @param toFindText
	 *            要查找的文本
	 * @return boolean true-查找到并选中该文本，false-未查找到文本
	 */

	public boolean find(String toFindText, Dispatch selection, ActiveXComponent word) {
		if (toFindText == null || toFindText.equals(""))
			return false;
		// 从selection所在位置开始查询
		Dispatch find = word.call(selection, "Find").toDispatch();
		// 设置要查找的内容
		Dispatch.put(find, "Text", toFindText);
		// 向前查找
		Dispatch.put(find, "Forward", "True");
		// 设置格式
		Dispatch.put(find, "Format", "True");
		// 大小写匹配
		Dispatch.put(find, "MatchCase", "True");
		// 全字匹配
		Dispatch.put(find, "MatchWholeWord", "True");
		// 查找并选中
		return Dispatch.call(find, "Execute").getBoolean();
	}

	/**
	 * 
	 * 获取临时文件
	 * 
	 * @return
	 */
	public File getTempFile() {
		File tempFile = new File("./", "temp.doc");
		return tempFile;
	}

	/**
	 * 
	 * POI读取word文本内容
	 * 
	 * @return
	 */
	public String getWordText(File tempFile) {
		//POI读取word模板

		FileInputStream in = null;
		try {
			in = new FileInputStream(tempFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		HWPFDocument hdt = null;
		try {
			hdt = new HWPFDocument(in);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Fields fields = hdt.getFields();
		Iterator<Field> it = fields.getFields(FieldsDocumentPart.MAIN).iterator();
		while (it.hasNext()) {
			System.out.println(it.next().getType());
		}
		//读取word文本内容
		Range range = hdt.getRange();
		String text = range.text();

		System.out.println("word文本内容：" + text);
		return text;
	}

	/**
	 * 打开文件
	 * @param documents	
	 * @param inputDocPath
	 * @return
	 */
	private Dispatch open(Dispatch documents, String inputDocPath) {
		return Dispatch.call(documents, "Open", inputDocPath).toDispatch();

	}

	/**
	 * 选定内容
	 * @param word
	 * @return
	 */
	private Dispatch select(ActiveXComponent word) {
		return word.getProperty("Selection").toDispatch();
	}

	/**
	 * 把插入点移动到文件首位置
	 * @param selection
	 */
	private void moveStart(Dispatch selection) {
		Dispatch.call(selection, "HomeKey", new Variant(6));
	}

	/**
	 * 从选定内容或插入点开始查找文本
	 * @param selection	选定内容
	 * @param toFindText	要查找的文本
	 * @return	true：查找到并选中该文本；false：未查找到文本。
	 */

	private boolean find(Dispatch selection, String toFindText) {
		//从selection所在位置开始查询
		Dispatch find = Dispatch.call(selection, "Find").toDispatch();
		//设置要查找的内容
		Dispatch.put(find, "Text", toFindText);
		//向前查找
		Dispatch.put(find, "Forward", "True");
		//设置格式
		Dispatch.put(find, "format", "True");
		//大小写匹配
		Dispatch.put(find, "MatchCase", "True");
		//全字匹配
		Dispatch.put(find, "MatchWholeWord", "True");
		//查找并选中
		return Dispatch.call(find, "Execute").getBoolean();
	}

	/**
	 * 把选定内容替换为设定文本
	 * @param selection
	 * @param newText
	 */
	private void replace(Dispatch selection, String newText) {
		Dispatch.put(selection, "Text", newText);
	}

	/**
	 * 全局替换
	 * @param selection	
	 * @param oldText
	 * @param replaceObj
	 */
	private void replaceAll(Dispatch selection, String oldText, Object replaceObj) {
		moveStart(selection);
		String newText = (String) replaceObj;
		while (find(selection, oldText)) {
			replace(selection, newText);
			Dispatch.call(selection, "MoveRight");
		}
	}

	/**
	 * 打印
	 * @param document
	 */
	private void print(Dispatch document) {
		Dispatch.call(document, "PrintOut");
	}

	/**
	 * 保存文件
	 * @param word
	 * @param outputPath
	 */
	private void save(ActiveXComponent word, String outputPath, PrintTemplate printTemplate, Dispatch document) {
		Dispatch.call(Dispatch.call(word, "WordBasic").getDispatch(), "FileSaveAs", outputPath);
		if (printTemplate.getIsReadOnly()) {
			new File(outputPath).setWritable(false);//将输出文档设为只读
		}
	}

	/**
	 * 关闭文件
	 * @param doc
	 */
	private void close(Dispatch doc) {
		Dispatch.call(doc, "Close", new Variant(true));
	}

	/**
	* 
	* 关键字定义（实体集合）
	* 
	* @return
	* @throws Exception 
	*/

	private static <ENTITY extends IdEntity> Map<String, Object> getMapValue(Collection<ENTITY> entities,
			Map<String, Object> dytableMap) throws Exception {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);//获取年份  
		int month = cal.get(Calendar.MONTH) + 1;//获取月份   
		int day = cal.get(Calendar.DATE);//获取日   
		int hour = cal.get(Calendar.HOUR_OF_DAY);//小时   
		int minute = cal.get(Calendar.MINUTE);//分              
		int second = cal.get(Calendar.SECOND);//秒   

		Map<String, Object> keyWordMap = new HashMap<String, Object>();
		keyWordMap.put("年", String.valueOf(year));
		keyWordMap.put("月", DateUtil.getFormatDate(month));
		keyWordMap.put("日", DateUtil.getFormatDate(day));
		keyWordMap.put("时", DateUtil.getFormatDate(hour));
		keyWordMap.put("分", DateUtil.getFormatDate(minute));
		keyWordMap.put("秒", DateUtil.getFormatDate(second));
		keyWordMap.put("简年", String.valueOf(year).substring(2));
		keyWordMap.put("大写年", toChinese(String.valueOf(year)));
		keyWordMap.put("大写月", toChinese(String.valueOf(month)));
		keyWordMap.put("大写日", toChinese(String.valueOf(day)));

		keyWordMap.put("YEAR", String.valueOf(year));
		keyWordMap.put("MONTH", String.valueOf(month));
		keyWordMap.put("DAY", String.valueOf(day));
		keyWordMap.put("HOUR", String.valueOf(hour));
		keyWordMap.put("MINUTE", String.valueOf(minute));
		keyWordMap.put("SECOND", String.valueOf(second));
		keyWordMap.put("SHORTYEAR", String.valueOf(year).substring(2));

		keyWordMap.put("当前登录人", SpringSecurityUtils.getCurrentUserName());

		//动态表单解析
		keyWordMap.put("dytable", dytableMap);

		//只传入${属性}时解析
		for (Object obj : entities) {
			//反射取得实体类的属性及值存入keyWordMap集合中
			BeanWrapperImpl wrapper = new BeanWrapperImpl(obj);
			PropertyDescriptor[] descriptors = wrapper.getPropertyDescriptors();
			for (PropertyDescriptor descriptor : descriptors) {
				String propertyName = descriptor.getName();
				System.out.println(propertyName);
				keyWordMap.put(propertyName, wrapper.getPropertyValue(propertyName));
			}
		}

		//传入${类.属性}时解析
		for (Object obj : entities) {
			String className = obj.getClass().getSimpleName();
			String lowerCaseName = className.substring(0, 1).toLowerCase() + className.substring(1);
			System.out.println("首字母小写类名：" + lowerCaseName);
			keyWordMap.put(lowerCaseName, obj);
		}

		return keyWordMap;
	}

	/**
	* 日期转为中文大写
	*/

	private static final String[] NUMBERS = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };

	public static synchronized String toChinese(String str) {
		StringBuffer sb = new StringBuffer();
		sb.append(getSplitDateStr(str, 0));
		return sb.toString();
	}

	public static String getSplitDateStr(String str, int unit) {
		// unit是单位 0=年 1=月 2日
		String[] DateStr = str.split("-");
		if (unit > DateStr.length)
			unit = 0;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < DateStr[unit].length(); i++) {

			if ((unit == 1 || unit == 2) && Integer.valueOf(DateStr[unit]) > 9) {
				sb.append(convertNum(DateStr[unit].substring(0, 1))).append("拾")
						.append(convertNum(DateStr[unit].substring(1, 2)));
				break;
			} else {
				sb.append(convertNum(DateStr[unit].substring(i, i + 1)));
			}
		}
		if (unit == 1 || unit == 2) {
			return sb.toString().replaceAll("^壹", "").replace("零", "");
		}
		return sb.toString();
	}

	private static String convertNum(String str) {
		return NUMBERS[Integer.valueOf(str)];
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	* 根据类的完全限定名获取表名
	* 
	* @param classtype
	* @return
	*/

	public String getTableName(Class classtype) {
		Annotation[] anno = classtype.getAnnotations();
		String tableName = "";
		for (int i = 0; i < anno.length; i++) {
			if (anno[i] instanceof Table) {
				Table table = (Table) anno[i];
				System.out.println(table.name());
				tableName = table.name();
			}
		}
		return tableName;
	}

	/**
	 * 是否保存到源文档
	 */
	@Override
	public Boolean isSaveToSource(String templateId) {
		PrintTemplate printTemplate = printTemplateDao.findUniqueBy("id", templateId);
		Boolean isSaveSource = printTemplate.getIsSaveSource();
		return isSaveSource;
	}

	/**
	 * 
	 * 获取随机数(年月日时分秒+五位随机数)
	 * 
	 * @return
	 */
	public static String getFileName() {
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		int temp = (int) (Math.random() * 100000);
		while (temp / 10000 < 1) {
			temp = (int) (Math.random() * 100000);
		}
		return sf.format(date) + temp;
	}

	/**
	 * 
	 * 获取所有可用的打印模板定义
	 * 
	 * @return
	 */
	@Override
	public List<PrintTemplate> findAll() {
		return printTemplateDao.getAll();
	}

	@Override
	public void save(PrintTemplate printTemplate) {
		printTemplateDao.save(printTemplate);
	}

	/**
	 * 
	 * 批量删除
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.printtemplate.service.PrintTemplateService#deleteAllById(java.lang.String[])
	 */
	@Override
	public void deleteAllById(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			PrintTemplate printTemplate = printTemplateDao.getById(ids[i]);
			printTemplateDao.delete(printTemplate);
		}
	}

	/**
	 * 根据打印模板ID获取对应的模板实体对象
	 * 
	 * @param printTemplateId
	 * @return
	 */
	public PrintTemplate getPrintTemplateById(String printTemplateId) {
		return printTemplateDao.getById(printTemplateId);
	}

	/**
	 * 如何描述该方法
	 * 
	 * @param example
	 * @return
	 */
	public List<PrintTemplate> findByExample(PrintTemplate printTemplate) {
		return printTemplateDao.findByExample(printTemplate);
	}
}
