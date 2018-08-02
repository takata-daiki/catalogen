package com.huataisi.oa.crm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.huataisi.oa.domain.User;
import com.huataisi.oa.security.UserUtil;
import com.huataisi.oa.util.ContainerUtils;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class UpLoadFileWindow extends Window{
	Logger logger = LoggerFactory.getLogger(UpLoadFileWindow.class);
	static ByteArrayOutputStream outStream = null;
	public UpLoadFileWindow(){

		setHeight("400px");
		setWidth("400px");
		this.setModal(true);
		this.center();

		Upload upload = new Upload();
		upload.setButtonCaption("开始上传");
		//TODO 导入
		//this.addComponent(upload);

	}
	private Label state = new Label();
	private Label fileName = new Label();
	private Label textualProgress = new Label();

	private ProgressIndicator pi = new ProgressIndicator();
	private Worker1 worker1;

	@SuppressWarnings("serial")
	private Upload upload = new Upload(null, new Receiver() {
		@Override
		public OutputStream receiveUpload(String filename, String mimeType) {
			outStream  = new ByteArrayOutputStream();
			return outStream;
		}
	});

	@SuppressWarnings("serial")
	public UpLoadFileWindow(String caption) {
		super(caption);
		setHeight("350px");
		setWidth("400px");
		this.setModal(true);
		this.center();
		//TODO 导入
//		Link link = new Link("模板下载", new ExternalResource("VAADIN/customer.xls"));
//		addComponent(link);
//		upload.setImmediate(true);
//		upload.setButtonCaption("选择需要上传的文件");
//		addComponent(upload);

		Panel p = new Panel("状态");
		//        p.setHeight("250px");
		p.setWidth("400");
		p.setSizeUndefined();
		FormLayout l = new FormLayout();
		l.setSizeFull();
		l.setMargin(true);
		p.setContent(l);
		HorizontalLayout stateLayout = new HorizontalLayout();
		stateLayout.setSpacing(true);
		stateLayout.addComponent(state);
		stateLayout.setCaption("当前状态");
		state.setValue("空闲");
		l.addComponent(stateLayout);
		fileName.setCaption("文件名");
		l.addComponent(fileName);
		pi.setCaption("当前进度");
		pi.setVisible(false);
		l.addComponent(pi);
		textualProgress.setVisible(false);
		l.addComponent(textualProgress);
//TODO 导入
		//addComponent(p);

		upload.addListener(new Upload.StartedListener() {
			public void uploadStarted(StartedEvent event) {
				// this method gets called immediatedly after upload is
				// started
				pi.setValue(0f);
				pi.setVisible(true);
				pi.setPollingInterval(500); // hit server frequantly to get
				textualProgress.setVisible(true);
				// updates to client
				state.setValue("上传中,请稍候...");
				fileName.setValue(event.getFilename());
			}
		});

		upload.addListener(new Upload.ProgressListener() {
			public void updateProgress(long readBytes, long contentLength) {
				//				 this method gets called several times during the update
				pi.setValue(new Float(readBytes / (float) contentLength));
				textualProgress.setValue("上传进度 " + readBytes
						+ "bytes / " + contentLength+"bytes");
				//				try {
				//					Thread.sleep(100);
				//				} catch (InterruptedException e) {
				//					// TODO Auto-generated catch block
				//					e.printStackTrace();
				//				}
			}

		});

		upload.addListener(new Upload.SucceededListener() {
			public void uploadSucceeded(SucceededEvent event) {
				state.setValue("上传完成,开始导入");
				worker1 = new Worker1();
				worker1.start();
			}
		});


		upload.addListener(new Upload.FailedListener() {
			private static final long serialVersionUID = 1L;
			public void uploadFailed(FailedEvent event) {
				state.setValue("上传失败 ");
			}
		});


	}
	public void prosessed() {
		int i = worker1.getCurrent();
		int count = worker1.getCount();
		if (i == count) {
			pi.setEnabled(false);
			state.setValue("导入完成");
			pi.setValue(1f);
			JPAContainer<Customer> container = ContainerUtils.createJPAContainer(Customer.class);
			container.refresh();
		} else {
			pi.setValue((float) i / count);
		}
		textualProgress.setValue("导入进度 " + i + "条 / " + count+"条");
	}
	public class Worker1 extends Thread {
		int i = 1;
		private int countRows ;

		@Override
		@Transactional
		public void run() {
			Customer cus = null;
			try{
				ByteArrayInputStream bin = new ByteArrayInputStream(outStream.toByteArray());
				HSSFWorkbook wb = new HSSFWorkbook(bin);
				HSSFSheet st = wb.getSheetAt(0);
				//从wb中取数据
				countRows = st.getPhysicalNumberOfRows()-1;

				User nu = new User();

				for (; i <= countRows; i++) {

					cus = new Customer();
					HSSFRow row = st.getRow(i);
					if(row != null && row.getCell(0) != null && !"".equals(row.getCell(0).getStringCellValue())){
						cus.setName(getStringValue(row.getCell(0)));						

						//
						if(row.getCell(2) != null){
							String strIstry = getStringValue(row.getCell(2));
							Industry istry = new Industry().findIndustryByName(strIstry);
							if (istry == null && strIstry != null && !"".equals(strIstry)) {
								istry = new Industry(strIstry);
								istry.persist();
							}						
							cus.setIndustry(istry);
						}
						if(row.getCell(11) != null){
							cus.setCategory(CusCategory.valueOf(getStringValue(row.getCell(11))));
						}
						//地区
						if(row.getCell(12) != null){
							String strArea = getStringValue(row.getCell(12));
							Area area = new Area().getAreaByName(strArea);
							if (area == null && strArea != null && !"".equals(strArea)) {
								area = new Area(strArea, null);
								area.persist();
							}						
							cus.setArea(area);
						}
						//客户类型
						if(row.getCell(13) != null){
							String strCusType = getStringValue(row.getCell(13));
							CusType cusType = new CusType().findCusTypeByName(strCusType);
							if (cusType == null && strCusType != null && !"".equals(strCusType)) {
								cusType = new CusType(strCusType);
								cusType.persist();
							}						
							cus.setCustomerType(cusType);
						}

						nu = UserUtil.getCurrentUser();
						if(row.getCell(1) != null && !"".equals(row.getCell(1).getStringCellValue())){
							cus.setScale(Integer.valueOf(row.getCell(1).getStringCellValue()));
						}					
						cus.setPhone(getStringValue(row.getCell(3)));
						if(row.getCell(4) != null){
							cus.setUseSoft(row.getCell(4).getBooleanCellValue());						
						}
						cus.setUrl(getStringValue(row.getCell(5)));
						cus.setAddress(getStringValue(row.getCell(6)));
						cus.setRemark(getStringValue(row.getCell(7)));
						if(row.getCell(8) != null){
							cus.setNextContactDate(getDateValue(row.getCell(8)));						
						}
						cus.setCreateBy(nu);
						Date nextLDate = null;
						if(row.getCell(10) != null){
							cus.setLastTrackDate(getDateValue(row.getCell(10)));						
						}
						if(row.getCell(14) != null || row.getCell(15) != null || row.getCell(16) != null){
							Track track = new Track();
							if(row.getCell(14) != null){
								track.setTtime(getDateValue(row.getCell(14)));
							}
							track.setRemark(getStringValue(row.getCell(15)));
							track.setNextPlan(getStringValue(row.getCell(16)));
							cus.getTracks().add(track);
						}
						if(row.getCell(17) != null){
							Contact contact = new Contact(getStringValue(row.getCell(17)));
							contact.setEmail(getStringValue(row.getCell(18)));
							contact.setQq(getStringValue(row.getCell(19)));
							contact.setPhone(getStringValue(row.getCell(20)));
							contact.setTel(getStringValue(row.getCell(21)));
							cus.getContacts().add(contact);	
						}

						//					CusTypeHistory cusTypeHistory  = new CusTypeHistory(row.getCell(18).getStringCellValue(), 
						//							row.getCell(19).getDateCellValue());
						//					cus.getTypeTrack().add(cusTypeHistory);

						cus.persist();
						Thread.sleep(10);
					}
					// All modifications to Vaadin components should be synchronized
					// over application instance. For normal requests this is done
					// by the servlet. Here we are changing the application state
					// via a separate thread.
					//TODO 导入
//					synchronized (getApplication()) {
//						prosessed();
//					}


				}
			}catch (Exception e) {
				if(e.getMessage()!= null && e.getMessage().indexOf("Duplicate") != -1){
					state.setValue("记录重复："+cus.getName());
				}else{
					state.setValue("导入出错");
				}
				pi.setEnabled(true);
				e.printStackTrace();
				throw new RuntimeException("导入出错");
			}
		}

		public int getCurrent() {
			return i;
		}
		public int getCount(){
			return countRows;
		}
		private String getStringValue(HSSFCell cell){
			if(cell != null){
				int type = cell.getCellType();
				if(type == HSSFCell.CELL_TYPE_STRING){
					return cell.getStringCellValue();
				}else if(type == HSSFCell.CELL_TYPE_NUMERIC){
					BigDecimal big = new BigDecimal(cell.getNumericCellValue());
					return  big.longValue()+"";
				}else{
					//					return cell.get
				}
			}
			return "";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
		private Date getDateValue(HSSFCell cell){
			if(cell != null){
				int type = cell.getCellType();
				if(type == HSSFCell.CELL_TYPE_STRING){
					String s =  cell.getStringCellValue();
					try{
						return sdf.parse(s);
					}catch(Exception e){
						try {
							return sdf2.parse(s);
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}else if(type == HSSFCell.CELL_TYPE_NUMERIC){
					return cell.getDateCellValue();
				}else{
					//					return cell.get
				}
			}
			return null;
		}
	}
}

