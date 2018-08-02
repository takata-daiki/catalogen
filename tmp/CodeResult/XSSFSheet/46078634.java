//Copyright (C) 2010  Novabit Informationssysteme GmbH
//
//This file is part of Nuclos.
//
//Nuclos is free software: you can redistribute it and/or modify
//it under the terms of the GNU Affero General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//Nuclos is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU Affero General Public License for more details.
//
//You should have received a copy of the GNU Affero General Public License
//along with Nuclos.  If not, see <http://www.gnu.org/licenses/>.
package org.nuclos.client.nuclet.generator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.nuclos.client.nuclet.generator.content.EntityFieldGroupNucletContentGenerator;
import org.nuclos.client.nuclet.generator.content.EntityFieldNucletContentGenerator;
import org.nuclos.client.nuclet.generator.content.EntityNucletContentGenerator;
import org.nuclos.client.ui.Errors;
import org.nuclos.common.E;
import org.nuclos.common.NuclosBusinessException;
import org.nuclos.common.SpringApplicationContextHolder;
import org.nuclos.common.UID;
import org.nuclos.common.dal.vo.EntityObjectVO;
import org.nuclos.common.dbtransfer.NucletContentHashMap;
import org.nuclos.common.dbtransfer.NucletContentMap;
import org.nuclos.common.dbtransfer.NucletContentUID;
import org.nuclos.common.dbtransfer.TransferConstants;
import org.nuclos.common2.InternalTimestamp;
import org.nuclos.common2.LocaleInfo;
import org.nuclos.common2.SpringLocaleDelegate;
import org.nuclos.common2.StringUtils;
import org.nuclos.server.common.ejb3.LocaleFacadeRemote;
import org.nuclos.server.dbtransfer.TransferFacadeRemote;

public class NucletGenerator implements TransferConstants {
	
	private static final Logger LOG = Logger.getLogger(NucletGenerator.class);
	
	public static final String XLSX_FILE_VERSION = "1.0";
	
	public static final String XLSX_FILE = "/org/nuclos/client/nuclet/generator/NucletGeneration.xlsx";
	
	public static final String XLSX_FILE_SHEET_VERSION = "_(Version)_";
	
	// former Spring injection
	
	private TransferFacadeRemote transferFacade;
	
	private LocaleFacadeRemote localeFacade;
	
	private SpringLocaleDelegate localeDelegate;
	
	// end of former Spring injection
	
	private Collection<LocaleInfo> locales;
	
	private long id;
	
	private XSSFWorkbook workbook;
	
	private Map<LocaleInfo, Map<String, String>> localeResources;
	
	private EntityNucletContentGenerator genEntity;
	
	private EntityFieldNucletContentGenerator genEntityField;
	
	private EntityFieldGroupNucletContentGenerator genEntityFieldGroup;
	
	private NucletGeneratorLayoutMLFactory layoutMLFactory;
	
	private List<EntityObjectVO<UID>> layouts;
	
	private List<EntityObjectVO<UID>> layoutUsages;
	
	private final UID nulcetId = new UID();
	
	private String path;
	
	private String nucletFileName;
	
	private String nucletName;
	
	private EntityObjectVO<UID> nucletVO;
	
	private NucletContentUID.Map uidMap;
	
	public NucletGenerator() {
		setTransferFacadeRemote(SpringApplicationContextHolder.getBean(TransferFacadeRemote.class));
		setLocaleFacadeRemote(SpringApplicationContextHolder.getBean(LocaleFacadeRemote.class));
		setSpringLocaleDelegate(SpringApplicationContextHolder.getBean(SpringLocaleDelegate.class));
		init();
	}
	
	final void setTransferFacadeRemote(TransferFacadeRemote transferFacade) {
		this.transferFacade = transferFacade;
	}
	
	final TransferFacadeRemote getTransferFacadeRemote() {
		return transferFacade;
	}
	
	final void setLocaleFacadeRemote(LocaleFacadeRemote localeFacade) {
		this.localeFacade = localeFacade;
	}
	
	final LocaleFacadeRemote getLocaleFacadeRemote() {
		return localeFacade;
	}
	
	final void setSpringLocaleDelegate(SpringLocaleDelegate localeDelegate) {
		this.localeDelegate = localeDelegate;
	}
	
	final SpringLocaleDelegate getSpringLocaleDelegate() {
		return localeDelegate;
	}
	
	void init() {
		locales = getLocaleFacadeRemote().getAllLocales(false);
		localeResources = new HashMap<LocaleInfo, Map<String, String>>();
	}
	
	/**
	 * use File Chooser
	 */
	public void createEmptyXLSXFile() {
		final JFileChooser filechooser = new JFileChooser();
		final FileFilter filefilter = new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isDirectory() || file.getName().toLowerCase().endsWith("xlsx");
			}
			@Override
			public String getDescription() {
				return "Microsoft Excel (xlsx)";
			}
		};
		filechooser.addChoosableFileFilter(filefilter);
		filechooser.setFileFilter(filefilter);
		
		final int iBtn = filechooser.showSaveDialog(null);

		if (iBtn == JFileChooser.APPROVE_OPTION) {
			final File file = filechooser.getSelectedFile();
			if (file != null) {
				String xlsxFile = file.getAbsolutePath();
				if (!xlsxFile.toLowerCase().endsWith("xlsx")) {
					xlsxFile = xlsxFile + ".xlsx";
				}
				createEmptyXLSXFile(xlsxFile);
			}
		}
	}
	
	/**
	 * 
	 * @param xlsxFile
	 */
	public void createEmptyXLSXFile(String xlsxFile) {
		try {
			InputStream is = NucletGenerator.class.getResourceAsStream(XLSX_FILE);
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();

	        int c = 0;
	        while ((c = is.read()) != -1) {
	            bos.write((char) c);
	        }
	        
	        FileOutputStream fos = new FileOutputStream(xlsxFile);
		    fos.write(bos.toByteArray());
		    fos.close();
	        
		    info("Empty XLSX file created: " + xlsxFile);
		    
		} catch (FileNotFoundException e) {
			LOG.error(e.getMessage(), e);
			Errors.getInstance().showExceptionDialog(null, e);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
			Errors.getInstance().showExceptionDialog(null, e);
		}
	}
	
	/**
	 * use File Chooser
	 */
	public void generateNucletFromXLSX() {
		info("Nuclet generation started...");
		
		final JFileChooser filechooser = new JFileChooser();
		final FileFilter filefilter = new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isDirectory() || file.getName().toLowerCase().endsWith("xlsx");
			}
			@Override
			public String getDescription() {
				return "Microsoft Excel (xlsx)";
			}
		};
		filechooser.addChoosableFileFilter(filefilter);
		filechooser.setFileFilter(filefilter);
		
		final int iBtn = filechooser.showOpenDialog(null);

		if (iBtn == JFileChooser.APPROVE_OPTION) {
			final File file = filechooser.getSelectedFile();
			if (file != null) {
				try {
					path = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator));
					nucletName = file.getName();
					nucletName = nucletName.substring(0, nucletName.lastIndexOf("."));
					nucletFileName = nucletName + NUCLET_FILE_EXTENSION;
					generateNucletFromXLSX(new FileInputStream(file));
				} catch (FileNotFoundException e) {
					LOG.error(e.getMessage(), e);
					Errors.getInstance().showExceptionDialog(null, e);
				}
			}
		}
	}
	
	
	/**
	 * 
	 * @param xlsxFile
	 */
	public void generateNucletFromXLSX(String xlsxFile) {
		info("Nuclet generation started...");
		
		try {
			path = xlsxFile.substring(0, xlsxFile.lastIndexOf(File.separator));
			nucletName = xlsxFile.substring(xlsxFile.lastIndexOf(File.separator)+1, xlsxFile.length());
			nucletName = nucletName.substring(0, nucletName.lastIndexOf("."));
			nucletFileName = nucletName + NUCLET_FILE_EXTENSION;
			generateNucletFromXLSX(new FileInputStream(xlsxFile));
		} catch (FileNotFoundException e) {
			LOG.error(e.getMessage(), e);
			Errors.getInstance().showExceptionDialog(null, e);
		}
	}
	
	
	/**
	 * 
	 * @param xlsxFile
	 */
	private void generateNucletFromXLSX(InputStream xlsxFile) {
		try { 
			workbook = new XSSFWorkbook(xlsxFile);
			
			checkFileVersion();
			
			// step 1. Entities
			genEntity = new EntityNucletContentGenerator(this);
			genEntity.generateEntityObjects();
			// step 2. Entity field groups
			genEntityFieldGroup = new EntityFieldGroupNucletContentGenerator(this);
			genEntityFieldGroup.generateEntityObjects();
			// step 3. Entity fields
			genEntityField = new EntityFieldNucletContentGenerator(this, genEntity, genEntityFieldGroup);
			genEntityField.generateEntityObjects();
			
			// step 4. Layouts
			layoutMLFactory = new NucletGeneratorLayoutMLFactory(this);
			layouts = generateLayouts();
			layoutUsages = generateLayoutUsages();
			
			// step 5. Nuclet
			nucletVO = createNucletEntityObjectVO();
			
			// step 6. UIDs
			//uidMap = generateUIDs();
			
			// step 7. generate File
			byte[] bytes = generateNucletFile();
			
			// step 8. write File
			String nucletFile = path + File.separator + nucletFileName;
			FileOutputStream fos = new FileOutputStream(nucletFile);
		    fos.write(bytes);
		    fos.close();
		    
		    info("Nuclet generated: " + nucletFile);
						
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
			Errors.getInstance().showExceptionDialog(null, e);
		} catch (NuclosBusinessException e) {
			LOG.error(e.getMessage(), e);
			Errors.getInstance().showExceptionDialog(null, e);
		} 
	}
	
	private byte[] generateNucletFile() throws NuclosBusinessException {
		NucletContentMap nucletContentMap = new NucletContentHashMap();
		
		return transferFacade.createTransferFile(nucletContentMap.getValues(E.NUCLET).get(0), nucletContentMap);		
	}
	
	private List<EntityObjectVO<UID>> generateLayouts() {
		List<EntityObjectVO<UID>> result = new ArrayList<EntityObjectVO<UID>>();
		for (EntityObjectVO<UID> eoEntity : genEntity.getResult()) {
			final UID entity = eoEntity.getFieldValue(E.ENTITY.entity.getUID(), UID.class);
			try {
				final String layoutML = layoutMLFactory.generateLayout(entity, false, false, true);
				final EntityObjectVO<UID> eo = new EntityObjectVO<UID>(entity);
				eo.setPrimaryKey(getNextId());
				//eo.setFieldValue(E.LAYOUT.entity.getUID(), entity); // for usages @TODO MultiNuclet. 
				eo.setFieldValue(E.LAYOUT.name.getUID(), entity);
				eo.setFieldValue(E.LAYOUT.description.getUID(), entity);
				eo.setFieldValue(E.LAYOUT.layoutML.getUID(), layoutML);
				setMetaFields(eo);
				
				result.add(eo);
				
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				error(String.format("During layout generation for entity \"%s\" an error has occurred: %s", entity, e.getMessage()));
			}
		}
		return result;
	}
	
	private List<EntityObjectVO<UID>> generateLayoutUsages() {
		List<EntityObjectVO<UID>> result = new ArrayList<EntityObjectVO<UID>>();
		for (EntityObjectVO<?> eoLayout : layouts) {
			for (int i = 0; i < 2; i++) {
				final EntityObjectVO<UID> eo = new EntityObjectVO<UID>(E.LAYOUTUSAGE.getUID());
				eo.setPrimaryKey(getNextId());
				eo.setFieldValue(E.LAYOUTUSAGE.entity.getUID(), eoLayout.getFieldValue(E.LAYOUTUSAGE.entity.getUID(), String.class));
				eo.setFieldValue(E.LAYOUTUSAGE.getUID(), i==0);
				eo.setFieldId(E.LAYOUTUSAGE.layout.getUID(), (Long)eoLayout.getPrimaryKey()); //@TODO MultiNuclet. 
				eo.setFieldValue(E.LAYOUTUSAGE.layout.getUID(), eoLayout.getFieldValue(E.LAYOUTUSAGE.layout.getUID(), String.class));				
				setMetaFields(eo);
				
				result.add(eo);
			}
		}
		return result;
	}
	
	private EntityObjectVO<UID> createNucletEntityObjectVO() {
		final EntityObjectVO<UID> result = new EntityObjectVO<UID>(E.NUCLET.getUID());
		result.setPrimaryKey(nulcetId);
		result.setFieldValue(E.NUCLET.name.getUID(), nucletName);
		result.setFieldValue(E.NUCLET.packagefield.getUID(), nucletName);
		setMetaFields(result);
		
		return result;
	}
	
	protected List<EntityObjectVO<UID>> getEntities() {
		return genEntity.getResult();
	}
	
	protected List<EntityObjectVO<UID>> getEntityFields() {
		return genEntityField.getResult();
	}
	
	protected List<EntityObjectVO<UID>> getEntityFieldGroups() {
		return genEntityFieldGroup.getResult();
	}
	
	private void checkFileVersion() throws NuclosBusinessException {
		final XSSFSheet sheet = workbook.getSheet(XLSX_FILE_SHEET_VERSION);
		for (Row row : sheet) {
			switch (row.getRowNum()) {
				case 0:
					continue; // header row
				case 1:
					String value = row.getCell(0).getStringCellValue();
					if (!StringUtils.equalsIgnoreCase(value, XLSX_FILE_VERSION)) {
						throw new NuclosBusinessException(
								String.format("Wrong File Version \"%s\")! Current Nuclos Generation Version is \"%s\".",
										value, XLSX_FILE_VERSION));
					}
				default:
					return;
			}
		}
	}
	
	protected String getResourceText(String resourceId) {
		return localeResources.get(getSpringLocaleDelegate().getUserLocaleInfo()).get(resourceId);
	}
	
	public static void setMetaFields(EntityObjectVO eo) {
		eo.setCreatedBy("Nuclet Generator");
		eo.setChangedBy("Nuclet Generator");
		eo.setCreatedAt(new InternalTimestamp(System.currentTimeMillis()));
		eo.setChangedAt(new InternalTimestamp(System.currentTimeMillis()));
		eo.setVersion(1);
	}
	
	public void error(String error) {
		System.err.println(error);
	}
	
	public void warning(String warning) {
		System.out.println(warning);
	}
	
	public void info(String info) {
		System.out.println(info);
	}
	
	public XSSFWorkbook getWorkbook() {
		return workbook;
	}
	
	public UID getNextId() {
		return new UID();
	}
	
	public UID getNucletId() {
		return nulcetId;
	}
	
	public Collection<LocaleInfo> getLocales() {
		return locales;
	}
	
	public void addLocaleResource(LocaleInfo locale, String resourceId, String text) {
		if (!localeResources.containsKey(locale)) {
			localeResources.put(locale, new HashMap<String, String>());
		}
		localeResources.get(locale).put(resourceId, text);			
	}
	
	
}
