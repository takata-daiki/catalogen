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
package org.nuclos.client.statemodel.panels;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.prefs.Preferences;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;
import org.nuclos.client.common.MetaProvider;
import org.nuclos.client.common.security.SecurityCache;
import org.nuclos.client.entityobject.CollectableEntityObject;
import org.nuclos.client.genericobject.GenericObjectLayoutCache;
import org.nuclos.client.masterdata.MasterDataCache;
import org.nuclos.client.statemodel.models.StatePropertiesPanelModel;
import org.nuclos.client.statemodel.panels.rights.ExpandCollapseListener;
import org.nuclos.client.statemodel.panels.rights.MultiEditListener;
import org.nuclos.client.statemodel.panels.rights.RightAndMandatoryColumnHeader;
import org.nuclos.client.statemodel.panels.rights.RightAndMandatoryConstants;
import org.nuclos.client.statemodel.panels.rights.RightAndMandatoryRow;
import org.nuclos.client.statemodel.panels.rights.RightTransfer;
import org.nuclos.client.statemodel.panels.rights.RightTransfer.RoleRight;
import org.nuclos.client.statemodel.panels.rights.RightTransfer.RoleRights;
import org.nuclos.client.statemodel.panels.rights.SelectionListener;
import org.nuclos.client.theme.NuclosThemeSettings;
import org.nuclos.client.ui.Bubble;
import org.nuclos.client.ui.ColorChooserButton;
import org.nuclos.client.ui.DefaultSelectObjectsPanel;
import org.nuclos.client.ui.Icons;
import org.nuclos.client.ui.SelectObjectsController;
import org.nuclos.client.ui.UIUtils;
import org.nuclos.client.ui.model.ChoiceList;
import org.nuclos.common.E;
import org.nuclos.common.EntityMeta;
import org.nuclos.common.FieldMeta;
import org.nuclos.common.SF;
import org.nuclos.common.SpringApplicationContextHolder;
import org.nuclos.common.UID;
import org.nuclos.common.collection.CollectionUtils;
import org.nuclos.common.collection.Pair;
import org.nuclos.common.collection.Predicate;
import org.nuclos.common.preferences.PreferencesUtils;
import org.nuclos.common2.ClientPreferences;
import org.nuclos.common2.EntityAndField;
import org.nuclos.common2.LangUtils;
import org.nuclos.common2.SpringLocaleDelegate;
import org.nuclos.common2.exception.CommonBusinessException;
import org.nuclos.common2.exception.PreferencesException;
import org.nuclos.server.masterdata.valueobject.MasterDataVO;
import org.nuclos.server.statemodel.valueobject.AttributegroupPermissionVO;
import org.nuclos.server.statemodel.valueobject.MandatoryColumnVO;
import org.nuclos.server.statemodel.valueobject.MandatoryFieldVO;
import org.nuclos.server.statemodel.valueobject.StateVO;
import org.nuclos.server.statemodel.valueobject.SubformGroupPermissionVO;
import org.nuclos.server.statemodel.valueobject.SubformPermissionVO;

import info.clearthought.layout.TableLayout;

/**
 * Panel containing the properties of a state.
 *
 * <br>Created by Novabit Informationssysteme GmbH
 * <br>Please visit <a href="http://www.novabit.de">www.novabit.de</a>
 *
 * @author	<a href="mailto:Boris.Sander@novabit.de">Boris Sander</a>
 * @author	<a href="mailto:Christoph.Radig@novabit.de">Christoph Radig</a>
 * @version 01.00.00
 */
public class StatePropertiesPanel extends JPanel {

	private static final Logger LOG = Logger.getLogger(StatePropertiesPanel.class);

	public class ResourceIconChooser extends JPanel {
		
		private final JList list;
		
		public ResourceIconChooser() {
			setLayout(new BorderLayout());
			
			Icon[] icons = Icons.getInstance().getStateIcons();
			
			list = new JList(icons) {
	
				@Override
				public int getScrollableUnitIncrement(Rectangle visibleRect,
	                                                  int orientation,
	                                                  int direction) {
	                int row;
	                if (orientation == SwingConstants.VERTICAL &&
	                      direction < 0 && (row = getFirstVisibleIndex()) != -1) {
	                    Rectangle r = getCellBounds(row, row);
	                    if ((r.y == visibleRect.y) && (row != 0))  {
	                        Point loc = r.getLocation();
	                        loc.y--;
	                        int prevIndex = locationToIndex(loc);
	                        Rectangle prevR = getCellBounds(prevIndex, prevIndex);
	
	                        if (prevR == null || prevR.y >= r.y) {
	                            return 0;
	                        }
	                        return prevR.height;
	                    }
	                }
	                return super.getScrollableUnitIncrement(
	                                visibleRect, orientation, direction);
	            }
	        };
	        
	        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
	        list.setCellRenderer(new ListCellRenderer() {
				@Override
				public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
					JPanel btnPanel = new JPanel(new BorderLayout());
					btnPanel.setOpaque(false);
					
					JButton btn = new JButton((ImageIcon) value);
					btn.setBorderPainted(false);
					btn.setContentAreaFilled(true);
					
					if (isSelected && index >= 0) {
						btn.setOpaque(true);
						btn.setBackground(NuclosThemeSettings.BACKGROUND_COLOR4);
						btnPanel.setBorder(BorderFactory.createLineBorder(NuclosThemeSettings.BACKGROUND_ROOTPANE, 1));
					} else {
						btn.setOpaque(false);
						btnPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
					}
					btnPanel.add(btn, BorderLayout.CENTER);
					
					return btnPanel;
				}
			});
	        list.setVisibleRowCount(-1);
	        
	        list.setDragEnabled(true);
	        list.setTransferHandler(new TransferHandler(""){
            	private final DataFlavor DATA_FLAVOUR = new DataFlavor(ImageIcon.class, "Images");
                public int getSourceActions(JComponent c) {
                    return TransferHandler.COPY_OR_MOVE;
                }

	        	@Override
	        	protected Transferable createTransferable(JComponent c) {
	                return new Transferable() {

	                    public DataFlavor[] getTransferDataFlavors() {
	                        return new DataFlavor[] {DATA_FLAVOUR};
	                    }

	                    public boolean isDataFlavorSupported(DataFlavor flavor) {
	                        return flavor.equals(DATA_FLAVOUR);
	                    }

	                    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
	                        return list.getSelectedValue();
	                    }
	                };

	        	}
	        });
	        
	        JScrollPane listScroller = new JScrollPane(list);
	        listScroller.setPreferredSize(new Dimension(250, 80));
	        listScroller.setAlignmentX(LEFT_ALIGNMENT);
	        
	        add(listScroller, BorderLayout.CENTER);
		}
		
		public int getSelectedIndex() {
			return list.getSelectedIndex();
		}
		
		public void addDoubleClickAction(MouseListener ml) {
			list.addMouseListener(ml);
		}
		public void addListSelectionListener(ListSelectionListener lsl) {
			list.addListSelectionListener(lsl);
		}
		
		public void removeListSelectionListener(ListSelectionListener lsl) {
			list.removeListSelectionListener(lsl);
		}
	}

	public static class StateDependantRightsPanel extends JPanel 
			implements RightAndMandatoryConstants, Closeable {
		
		public static final int LEFT_BORDER = 5;
		
		public final static String PREFS_NODE_SELECTEDROLES = "selectedRoles";
		
		private Set<UID> selectedRoles = new HashSet<UID>();
		
		private Set<UID> collapsedGroups = new HashSet<UID>();
		
		private boolean filterUnchangedAttributes = false;
		
		private boolean firstInstance = true;
		
		private JToolBar toolBar = UIUtils.createNonFloatableToolBar(JToolBar.HORIZONTAL);
		
		private JPanel main = new JPanel();
		
		private RightTransfer rightTransfer = null; 
		
		private ActionListener actionListenerForWidthChanged = null;
		
		private int scrollPosition = -1;
		
		private StateVO statevo = null;
		
		private List<ChangeListener> lstDetailsChangedListeners = new ArrayList<ChangeListener>();
		
		private Thread showBubbleThread = null;
		
		// former Spring injection
		
		private SpringLocaleDelegate localeDelegate;
		
		// end of former Spring injection
		
		private ChangeListener detailsChangedListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				for (ChangeListener cl : lstDetailsChangedListeners) {
					cl.stateChanged(e);
				}
			}
		};

		public StateDependantRightsPanel() {
			super(new BorderLayout());
			
			toolBar.setBorderPainted(false);
			this.add(toolBar, BorderLayout.NORTH);
			
			main.setBorder(BorderFactory.createEmptyBorder(5, LEFT_BORDER, 0, 0));
			main.setBackground(COLOR_BACKGROUND);
			JScrollPane scroll = new JScrollPane(main);
			scroll.getHorizontalScrollBar().setUnitIncrement(20);
			scroll.getVerticalScrollBar().setUnitIncrement(20);
			this.add(scroll, BorderLayout.CENTER);
			
			setSpringLocaleDelegate(SpringApplicationContextHolder.getBean(SpringLocaleDelegate.class));
		}
		
		final void setSpringLocaleDelegate(SpringLocaleDelegate cld) {
			this.localeDelegate = cld;
		}
		
		final SpringLocaleDelegate getSpringLocaleDelegate() {
			return localeDelegate;
		}
		
		public void addDetailsChangedListener(ChangeListener cl) {
			lstDetailsChangedListeners.add(cl);
		}

		public void removeDetailsChangedListener(ChangeListener cl) {
			lstDetailsChangedListeners.remove(cl);
		}
		
		private void detailsChanged() {
			for (ChangeListener cl : lstDetailsChangedListeners) {
				cl.stateChanged(new ChangeEvent(this));
			}
		}
		
		public RightTransfer getRightTransfer() {
			return rightTransfer;
		}
		
		/**
		 * uses a given statevo
		 * @param usages
		 */
		public void setup(List<CollectableEntityObject<UID>> usages) {
			this.setup(usages, statevo);
		}
		
		/**
		 * 
		 * @param usages
		 * @param statevo
		 */
		public void setup(List<CollectableEntityObject<UID>> usages, StateVO statevo) {
			this.statevo = statevo;
			toolBar.removeAll();
			main.removeAll();
			try {
				initMain(usages);
			}
			catch(CommonBusinessException e) {
				LOG.error("setup failed: " + e, e);
			}
			revalidate();
			repaint();
		}
		
		/**
		 * updates the statevo with rights and mandatory information from this properties panel
		 */
		public void updateStateVO() {
			if(rightTransfer != null && statevo != null) {
				RoleRights rr = rightTransfer.getAllRoleRights();
				statevo.getMandatoryFields().clear();
				statevo.getMandatoryColumns().clear();
				statevo.getUserRights().clear();
				statevo.getUserSubformRights().clear();
				for (UID role : rr.rights.keySet()) {
					RoleRight roleRight = rr.rights.get(role);
					for (UID group : roleRight.groupRights.keySet()) {
						Object groupRight = roleRight.groupRights.get(group);
						if (groupRight != null) {
							if (!roleRight.groupIsSubform.contains(group)) {
								statevo.getUserRights().addValue(role, 
										new AttributegroupPermissionVO(group, role, statevo.getId(), (Boolean)groupRight));
							} else {

								// store subform...
								Set<SubformGroupPermissionVO> groupPermissions = new HashSet<SubformGroupPermissionVO>();
								for (Pair<UID, String> subgroup : roleRight.subformGroups.getValues(group)) {
									Pair<UID, UID> subgroupkey = new Pair<UID, UID>(group, subgroup.getX());
									if (rr.rightsEnabled.contains(subgroupkey)) {
										Boolean writeable = roleRight.subformGroupRights.get(subgroupkey);
										if (writeable != null) {
											UID sfgroup = subgroupkey.getY();
											if (UID.UID_NULL.equals(sfgroup)) {
												sfgroup = null;
											}
											groupPermissions.add(new SubformGroupPermissionVO(subgroupkey.getX(), sfgroup, writeable));
										}
									}
								}
								boolean create = false;
								boolean delete = false;
								if (groupRight instanceof Integer) {
									create = (((Integer)groupRight) & 1) != 0;
									delete = (((Integer)groupRight) & 2) != 0;
								}
								if (create || delete || !groupPermissions.isEmpty()) {
									statevo.getUserSubformRights().addValue(role,
											new SubformPermissionVO(group, role, statevo.getId(), create, delete, groupPermissions));
								}
							}
						}
					}

				}
				for (UID mandatory : rr.mandatoryFields) {
					statevo.getMandatoryFields().add(new MandatoryFieldVO(mandatory, statevo.getId()));
				}
				for (Pair<UID, String> mandatoryColumn : rr.mandatoryColumns) {
					FieldMeta<?> fieldMeta = MetaProvider.getInstance().getEntityField(mandatoryColumn.getX());
					statevo.getMandatoryColumns().add(
							new MandatoryColumnVO(fieldMeta.getEntity(), mandatoryColumn.getX(), statevo.getId()));
				}
			}
		}
		
		@Override
		public synchronized void close() {
			if (showBubbleThread != null) {
				showBubbleThread.interrupt();
				showBubbleThread = null;
			}
		}
		
		@Override
		public void finalize() throws Throwable {
			if (showBubbleThread != null) {
				LOG.error("Still having a showBubbleThread???");
				close();
			}
			super.finalize();
		}
		
		/**
		 * 
		 * @param usages
		 * @throws CommonBusinessException
		 */
		private void initMain(List<CollectableEntityObject<UID>> usages) throws CommonBusinessException {
			final SpringLocaleDelegate localeDelegate = getSpringLocaleDelegate();
			
			final Collection<MasterDataVO<?>> roles = MasterDataCache.getInstance().get(E.ROLE.getUID());
			final SortedMap<String, UID> rolesSorted = getRolesSorted(roles);
			final List<UID> rolesSortOrder = getRolesSorted(rolesSorted);
			final Set<UID> modules = getModules(usages);
			final Set<UID> metaMandatory = new HashSet<UID>();
			final Map<UID, SortedSet<Attribute>> attributes = getAttributes(modules, metaMandatory);
			final Map<UID, String> attributeGroups = getAttributeGroups(attributes.keySet());
			final SortedMap<String, UID> attributeGroupsSorted = getAttributeGroupsSorted(attributeGroups);
			final SortedMap<SubForm,SortedSet<Attribute>> subFormWithGroups = getSubForms(modules, metaMandatory);
			final SortedSet<SubForm> chartDatasource = getChartDatasources(modules);
			
			main.setLayout(new TableLayout(new double[][]{new double[]{TableLayout.FILL},new double[]{TableLayout.PREFERRED, TableLayout.FILL}}));
			
			final JPanel header = new JPanel();
			final JPanel rows = new JPanel();
			final JScrollPane scrollRows = new JScrollPane(rows, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollRows.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			
			header.setBackground(COLOR_BACKGROUND);
			rows.setBackground(COLOR_BACKGROUND);
			
			main.add(header, "0,0");
			main.add(rows, "0,1");
			
			final double[] colSizes = getColumns(roles.size());
			final double[] rowSizes = getRows(attributeGroupsSorted, attributes, subFormWithGroups, chartDatasource);
			
			final TableLayout layoutHeader = new TableLayout(new double[][]{colSizes, new double[]{TableLayout.PREFERRED, GAP_LINEBREAK}});
			final TableLayout layoutRows = new TableLayout(new double[][]{new double[]{colSizes[0], TableLayout.PREFERRED}, rowSizes});
			
			header.setLayout(layoutHeader);
			rows.setLayout(layoutRows);
			
			final JToggleButton filter = new JToggleButton(localeDelegate.getMessage(
					"StatePropertiesPanel.11", "Filter Attribute"), Icons.getInstance().getIconFilter16());
			filter.setSelected(filterUnchangedAttributes);
			final JButton roleSelection = new JButton(localeDelegate.getMessage(
					"StatePropertiesPanel.12", "Benutzergruppen"), Icons.getInstance().getIconLDAP());
			final JButton help = new JButton(Icons.getInstance().getIconHelp());
			
			
			/**
			 * Header in northwest
			 */
			JPanel nw = new JPanel();
			nw.setLayout(new TableLayout(new double[][]{
				new double[]{TableLayout.FILL, TableLayout.PREFERRED, GAP_ROWHEADER+5},
				new double[]{TableLayout.FILL}
			}));
			if (!modules.isEmpty()) {
				nw.add(new HorizontalLabel(localeDelegate.getMessage(
						"StatePropertiesPanel.13", "Pflichtfeld")), "1,0,r,b");
			} else {
				nw.add(new JLabel(localeDelegate.getMessage(
						"StatePropertiesPanel.14", "Keine Verwendung angegeben!")), "1,0");
			}
			nw.setBackground(COLOR_BACKGROUND);
			
			header.add(nw, "1,0");
			
			if (modules.isEmpty())
				return;
			
			if (statevo == null) {
				return;
			}
			
			
			/**
			 * init columns
			 */
			int iCol = 2;
			final Map<RightAndMandatoryColumnHeader, Integer> colsInLayout = new HashMap<RightAndMandatoryColumnHeader, Integer>();
			final Map<UID, RightAndMandatoryColumnHeader> roleColumns = new HashMap<UID, RightAndMandatoryColumnHeader>();
			if (firstInstance) {
				firstInstance = false;
				Preferences prefs = ClientPreferences.getInstance().getUserPreferences().node("collect").node("entity").node(E.STATEMODEL.getEntityName());
				List<UID> selectedRolesFromPreferences = new ArrayList<UID>();
				try {
					selectedRolesFromPreferences = PreferencesUtils.getUidList(prefs, PREFS_NODE_SELECTEDROLES);
				}
				catch(Exception ex) {
					// on exception empty list
					LOG.warn("broken uid list in preferences");
				}
				boolean showBubble = false;
				if (selectedRolesFromPreferences.size() > 0) {
					for (UID role : rolesSortOrder) {
						if (selectedRolesFromPreferences.contains(role)) {
							selectedRoles.add(role);
						}
					}
					showBubble = selectedRoles.size() != rolesSortOrder.size();
				} else {
					if (rolesSortOrder.size() > 12) {
						for (int i = 0; i < 12; i++) {
							selectedRoles.add(rolesSortOrder.get(i));
						}
						showBubble = true;
					} else {
						selectedRoles.addAll(rolesSortOrder);
					}
				}
				
				if (showBubble) {
					final String msg = localeDelegate.getMessage(
							"StatePropertiesPanel.15", "Einige Benutzergruppen sind ausgeblendet.");
					
					synchronized(this) {
						if (showBubbleThread != null) {
							LOG.error("More than one showBubbleThread???");
							showBubbleThread.interrupt();
						}
						
						showBubbleThread = new Thread(new Runnable() {
							
							@Override
							public void run() {
								final Thread me = Thread.currentThread();
								boolean tryAgain = true;
								while (tryAgain) {
									tryAgain = tryAgain && !me.isInterrupted();
									try {
										 Thread.sleep(2000);
									}
									catch (InterruptedException e) {
										LOG.info("showBubbleThread: Panel closed; " + e);
										tryAgain = false;
									}
									tryAgain = tryAgain && !me.isInterrupted();
									if (!tryAgain) break;
									try {
										roleSelection.getLocationOnScreen();
										tryAgain = false;
										(new Bubble(roleSelection, msg, 
												5, Bubble.Position.SE)).setVisible(true);
									} catch (IllegalComponentStateException e) {
										// do nothing. it is not shown
									}
									tryAgain = tryAgain && !me.isInterrupted();
								}
							}						
						}, "StateModelProperties.showBubble()");
						showBubbleThread.start();
					}
				}
			}
			for (String role : rolesSorted.keySet()) {
				RightAndMandatoryColumnHeader col = new RightAndMandatoryColumnHeader(rolesSorted.get(role), role, detailsChangedListener);
				roleColumns.put(rolesSorted.get(role), col);
				header.add(col.initView(100), iCol + ",0");
				colsInLayout.put(col, iCol);
				if (!selectedRoles.contains(rolesSorted.get(role))) {
					layoutHeader.setColumn(iCol, 0);
				}
				iCol++;
			}
			JPanel lastGridOnTheRight = new JPanel() {
				@Override
				protected void paintComponent(Graphics g) {
					Graphics2D g2d = (Graphics2D) g;				
					g2d.setStroke(new BasicStroke(1.f));
					g2d.setColor(COLOR_MARKER_GRID);
					g2d.drawLine(0, 0, 0, CELL_HEIGHT-1);
					g2d.setColor(COLOR_GRID);
					g2d.drawLine(0, CELL_HEIGHT, 0, COLUMN_HEADER_HEIGHT_MAX);
				}
			};
			header.add(lastGridOnTheRight, iCol + ",0");
			
			class LastGridOfGroup extends JPanel {
				@Override
				protected void paintComponent(Graphics g) {
					Graphics2D g2d = (Graphics2D) g;				
					g2d.setStroke(new BasicStroke(1.f));
					g2d.setColor(COLOR_GRID);
					int rowHeader = Double.valueOf(layoutHeader.getColumn(1)).intValue();
					g2d.drawLine(0, 0, rowHeader-GAP_ROWHEADER-1, 0);
					g2d.drawLine(rowHeader, 0, rowHeader+(selectedRoles.size()*(CELL_WIDTH-1)), 0);
				};
			}
			
			/**
			 * Attributes with groups
			 */
			int iRow = 0;
			final Map<RightAndMandatoryRow, Integer> rowsInLayout = new HashMap<RightAndMandatoryRow, Integer>(); 
			final Map<RightAndMandatoryRow, Set<RightAndMandatoryRow>> groupsWithArttributes = new HashMap<RightAndMandatoryRow, Set<RightAndMandatoryRow>>(); 
			
			class GroupExpandCollapseListener extends ExpandCollapseListener {
				private final RightAndMandatoryRow row;
				public GroupExpandCollapseListener(RightAndMandatoryRow row) {
					super();
					this.row = row;
				}
				
				@Override
				public void expand() {
					collapsedGroups.remove(row.getId());
					row.setCollapsed(false);
					for (RightAndMandatoryRow attrRow : groupsWithArttributes.get(row)) {
						attrRow.setCollapsed(false);
						if (filterUnchangedAttributes && !attrRow.isMandatory() && !attrRow.isRightsEnabled()) {
							continue;
						}
						int rowInLayout = rowsInLayout.get(attrRow);
						layoutRows.setRow(rowInLayout, rowSizes[rowInLayout]); // back to default
					}
					rows.revalidate();
					rows.repaint();
				}
				@Override
				public void collapse() {
					collapsedGroups.add(row.getId());
					row.setCollapsed(true);
					for (RightAndMandatoryRow attrRow : groupsWithArttributes.get(row)) {
						attrRow.setCollapsed(true);
						layoutRows.setRow(rowsInLayout.get(attrRow), 0);
					}
					rows.revalidate();
					rows.repaint();
				}
			};
			
			for (String attributeGroup : attributeGroupsSorted.keySet()) {
				if (attributes.keySet().contains(attributeGroupsSorted.get(attributeGroup))) {
					final RightAndMandatoryRow rowGroup = 
							getRow(attributeGroupsSorted.get(attributeGroup),  true, null, attributeGroup, false, statevo, true, rolesSortOrder, null, detailsChangedListener);
					iRow = addGroupToLayout(iRow, rowGroup, rowsInLayout, groupsWithArttributes, rows);
					
					for (Attribute attribute : attributes.get(attributeGroupsSorted.get(attributeGroup))) {
						UID attributeId = attribute.getUID();
						RightAndMandatoryRow rowAttribute = getRow(null,  false, attributeId, attribute.getName(), metaMandatory.contains(attributeId), statevo, true, rolesSortOrder, rowGroup, detailsChangedListener);
						iRow = addAttributeToLayout(iRow, rowGroup, rowAttribute, rowsInLayout, groupsWithArttributes, rows);
					}
					
					rows.add(new LastGridOfGroup(), "1," + iRow);
					iRow++;
					
					//set expand/collapse listener for this group
					rowGroup.setExpandCollapseListener(new GroupExpandCollapseListener(rowGroup));
				}
			}
			
			// Subforms with columns
			iRow++; // GAP
			if (!subFormWithGroups.isEmpty()) {
				JLabel lab = new JLabel("<html><b>" + localeDelegate.getMessage("StatePropertiesPanel.16", "Unterformulare") + ":</b></html>");
				lab.setForeground(COLOR_SELECTION_BACKGROUND);
				rows.add(lab, "1," + iRow);
			}
			iRow++; 
			iRow++; // GAP
			
			for (SubForm subform : subFormWithGroups.keySet()) {
				final RightAndMandatoryRow rowGroup = getSubRow(subform.getMetaData().getUID(),  true, null, subform.getMetaData().getEntityName(),
						false, statevo, true, rolesSortOrder, null, detailsChangedListener);
				iRow = addGroupToLayout(iRow, rowGroup, rowsInLayout, groupsWithArttributes, rows);
				
				for (Attribute attribute : subFormWithGroups.get(subform)) {
					UID attributeId = attribute.getUID();
					RightAndMandatoryRow rowAttribute = getSubRow(subform.getMetaData().getUID(),  false, attributeId, attribute.getName(),
							metaMandatory.contains(attributeId), statevo, attribute.bGroup, rolesSortOrder, rowGroup, detailsChangedListener);
					rowAttribute.setSubGroup(attribute.bGroup);
					iRow = addAttributeToLayout(iRow, rowGroup, rowAttribute, rowsInLayout, groupsWithArttributes, rows);
				}

				rows.add(new LastGridOfGroup(), "1," + iRow);
				iRow++;
				
				//set expand/collapse listener for this group
				rowGroup.setExpandCollapseListener(new GroupExpandCollapseListener(rowGroup));
			}
			
			// Chart datasources without columns
			iRow++; // GAP
			if (!chartDatasource.isEmpty()) {
				JLabel lab = new JLabel("<html><b>" + localeDelegate.getMessage("StatePropertiesPanel.22", "Diagramm-Datenquellen") + ":</b></html>");
				lab.setForeground(COLOR_SELECTION_BACKGROUND);
				rows.add(lab, "1," + iRow);
			}
			iRow++; 
			iRow++; // GAP
			
			for (SubForm subform : chartDatasource) {
				final RightAndMandatoryRow rowGroup = getSubRow(subform.getMetaData().getUID(), false, null, subform.getMetaData().getEntityName(), false, statevo, true, rolesSortOrder, null, detailsChangedListener);
				iRow = addGroupToLayout(iRow, rowGroup, rowsInLayout, groupsWithArttributes, rows);
			}
			
			
			/**
			 * hide not selected roles in rows
			 */
			for (UID role : rolesSortOrder) {
				if (!selectedRoles.contains(role)) {
					for (RightAndMandatoryRow row : rowsInLayout.keySet()) {
						row.hideRoleRight(role);
					}
				}
			}
			
			
			/**
			 * restore collapsed groups
			 */
			for(UID group : collapsedGroups) {
				for (RightAndMandatoryRow rowGroup : groupsWithArttributes.keySet()) {
					if (group.equals(rowGroup.getId())) {
						if (rowGroup.getExpandCollapseListener() != null)
							rowGroup.getExpandCollapseListener().actionPerformed(
									new ActionEvent(rowGroup, 0, ExpandCollapseListener.COMMAND_COLLAPSE));
					}
				}
			}
			
			
			/**
			 * hide unchanged attributes
			 */
			final ActionListener filterUnchangedAttributesListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					filterUnchangedAttributes = filter.isSelected();
					
					for (RightAndMandatoryRow row : rowsInLayout.keySet()) {
						if (!row.isGroup()) {
							if (!row.isMandatory() && !row.isRightsEnabled()) {
								if (filterUnchangedAttributes) {
									layoutRows.setRow(rowsInLayout.get(row), 0);
								} else {
									if (!row.isCollapsed()) {
										int rowInLayout = rowsInLayout.get(row);
										layoutRows.setRow(rowInLayout, rowSizes[rowInLayout]); // back to default
									}
								}
							} else {
								// pasted rights in filtered attributes
								if (!row.isCollapsed()) {
									int rowInLayout = rowsInLayout.get(row);
									layoutRows.setRow(rowInLayout, rowSizes[rowInLayout]); // back to default
								}
							}
						}
					}
					
					rows.revalidate();
					rows.repaint();
				}
			};
			filterUnchangedAttributesListener.actionPerformed(new ActionEvent(filter, 0, "INIT_FILTER_UNCHANGED_ATTRIBUTES"));
			
			
			/**
			 * add selection listener
			 */
			SelectionListener sl = new SelectionListener() {
				@Override
				public void select(Integer role) {
					deselect();
					roleColumns.get(role).setSelected(true);
					for (RightAndMandatoryRow row : rowsInLayout.keySet()) {
						row.setSelected(role, true);
					}
				}

				@Override
				public void deselect() {
					for (RightAndMandatoryColumnHeader col : roleColumns.values()) {
						col.setSelected(false);
					}
					for (RightAndMandatoryRow row : rowsInLayout.keySet()) {
						row.removeAllSelections();
					}
				}
			};
			for (RightAndMandatoryColumnHeader col : roleColumns.values()) {
				col.setSelectionListener(sl);
			}
			for (RightAndMandatoryRow row : rowsInLayout.keySet()) {
				row.setSelectionListener(sl);
			}
			
			
			/**
			 * add multiedit column listener
			 */
			MultiEditListener mel = new MultiEditListener() {
				@Override
				public void setRoleRight(UID role, Boolean right) {
					for (RightAndMandatoryRow group : groupsWithArttributes.keySet()) {
						if (!group.getGroupId().equals(UID.UID_NULL)) {
							group.setRoleRight(role, right);
							for (RightAndMandatoryRow attr : groupsWithArttributes.get(group)) {
								attr.setRoleRight(role, right);
							}
						}
					}
					detailsChanged();
				}
			};
			for (RightAndMandatoryColumnHeader col : roleColumns.values()) {
				col.setMultieditListener(mel);
			}
			
			
			/**
			 * init righttransfer for copy & paste between complete role rights from one state to other,
			 * and for rights of one role to other.
			 * 
			 * also used to fill statevo. see <code>updateStateVO</code>
			 */
			rightTransfer = new RightTransfer(){
				@Override
				public RoleRights getAllRoleRights() {
					RoleRights rr = new RoleRights();
					for (UID role : rolesSortOrder) {
						rr.rights.put(role, getRoleRight(role));
					}
					for (RightAndMandatoryRow group : groupsWithArttributes.keySet()) {
						for (RightAndMandatoryRow attr : groupsWithArttributes.get(group)) {
							if (attr.isMandatory()) {
								if (attr.isSubForm()) {
									rr.mandatoryColumns.add(new Pair<UID, String> (attr.getId(), attr.getName()));
								} else {
									rr.mandatoryFields.add(attr.getId());
								}
							}
							if (attr.isRightsEnabled()) {
								if (attr.isSubForm() && attr.isSubGroup()) {
									rr.rightsEnabled.add(new Pair<UID, UID>(group.getId(), attr.getId()));
								}
							}
						}
					}
					return rr;
				}
				@Override
				public RoleRight getRoleRight(UID role) {
					RoleRight rr = new RoleRight();
					for (RightAndMandatoryRow group : groupsWithArttributes.keySet()) {
						rr.groupRights.put(group.getId(), group.getRoleRights().get(role));
						rr.groupNames.put(group.getId(), group.getGroupName());
						if (group.isSubForm()) rr.groupIsSubform.add(group.getId());
						for (RightAndMandatoryRow attr : groupsWithArttributes.get(group)) {
							if (attr.isSubForm() && attr.isSubGroup()) {
								rr.subformGroups.addValue(group.getId(), new Pair<UID, String>(attr.getId(), attr.getName()));
								if (attr.isRightsEnabled()) {
									Pair<UID, UID> subgroup = new Pair<UID, UID>(group.getId(), attr.getId());
									rr.subformGroupRights.put(subgroup, (Boolean)attr.getRoleRights().get(role));
								}
							}
						}
					}
					
					return rr;
				}
				@Override
				public void setAllRoleRights(RoleRights rr) {
					for (UID role : rolesSortOrder) {
						for (RightAndMandatoryRow group : groupsWithArttributes.keySet()) {
							group.setRoleRight(role, rr.rights.get(role).groupRights.get(group.getId()));
							for (RightAndMandatoryRow attr : groupsWithArttributes.get(group)) {
								attr.setMandatory(rr.mandatoryFields.contains(attr.getId()) ||
									rr.mandatoryColumns.contains(new Pair<UID, String>(attr.getId(), attr.getName())));
								Pair<UID, UID> subgroup = new Pair<UID, UID>(group.getId(), attr.getId());
								attr.setRightsEnabled(rr.rightsEnabled.contains(subgroup));
								attr.setRoleRight(role, rr.rights.get(role).subformGroupRights.get(subgroup));
							}
						}
					}
					filterUnchangedAttributesListener.actionPerformed(new ActionEvent(rr, 0, "PASTE_ALL_ROLE_RIGHTS"));
				}
				@Override
				public void setRoleRight(RoleRight rr) {
					for (UID role : roleColumns.keySet()) {
						if (roleColumns.get(role).isSelected()) {
							for (RightAndMandatoryRow group : groupsWithArttributes.keySet()) {
								group.setRoleRight(role, rr.groupRights.get(group.getId()));
								for (RightAndMandatoryRow attr : groupsWithArttributes.get(group)) {
									Pair<UID, UID> subgroup = new Pair<>(group.getId(), attr.getId());
									Boolean newAttributeRight = rr.subformGroupRights.get(subgroup);
									if (!attr.isRightsEnabled()) {
										// if group right differs from attribute right ... enable rights
										Boolean groupRight = (Boolean)group.getRoleRights().get(role);
										if (!LangUtils.equal(newAttributeRight, groupRight)) {
											attr.copyRightsFromGroup();
											attr.setRoleRight(role, newAttributeRight);
										}
									} else {
										attr.setRoleRight(role, newAttributeRight);
									}
								}
							}
						}
					}
					filterUnchangedAttributesListener.actionPerformed(new ActionEvent(rr, 0, "PASTE_ROLE_RIGHT"));
				}
			};
			for (RightAndMandatoryColumnHeader col : roleColumns.values()) {
				col.setRightTransfer(rightTransfer);
			}
			
			
			/**
			 * adjust row header width and column header height
			 */
			layoutHeader.setColumn(1, adjustRowHeaderWidth(rowsInLayout.keySet()) + GAP_ROWHEADER);
			layoutHeader.setRow(0, adjustColumnHeaderHeight(roleColumns.values()) + CELL_HEIGHT);
			
			
			/**
			 * set toolbar functions
			 */
			filter.addActionListener(filterUnchangedAttributesListener);
			toolBar.add(filter);
			roleSelection.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {		
					final RoleSelection roleSelectionCtrl = new RoleSelection(roleSelection);
					final ChoiceList<RightAndMandatoryColumnHeader> ro = new ChoiceList<RightAndMandatoryColumnHeader>();
					final Comparator<RightAndMandatoryColumnHeader> comp = new RightAndMandatoryColumnHeader.Comparator();
					
					ro.set(CollectionUtils.selectIntoSortedSet(roleColumns.values(),
							new Predicate<RightAndMandatoryColumnHeader>() {
								@Override
								public boolean evaluate(RightAndMandatoryColumnHeader t) {
									return !selectedRoles.contains(t.getId());
								}
							}, comp), 
							CollectionUtils.sorted(CollectionUtils.select(
							roleColumns.values(),
							new Predicate<RightAndMandatoryColumnHeader>() {
								@Override
								public boolean evaluate(RightAndMandatoryColumnHeader t) {
									return selectedRoles.contains(t.getId());
								}
							}), comp), comp);
					
					roleSelectionCtrl.setModel(ro);
					boolean changed = roleSelectionCtrl.run( 
							localeDelegate.getMessage("StatePropertiesPanel.17", "Benutzergruppenauswahl"));
					
					if (changed) {
						List<RightAndMandatoryColumnHeader> selectedColsChanged = (List<RightAndMandatoryColumnHeader>) roleSelectionCtrl.getSelectedObjects();
						Set<UID> selectedRolesNew = new HashSet<UID>();
						
						for (RightAndMandatoryColumnHeader col : colsInLayout.keySet()) {
							if (selectedColsChanged.contains(col)) {
								selectedRolesNew.add(col.getId());
								
								if (!selectedRoles.contains(col.getId())) {
									layoutHeader.setColumn(colsInLayout.get(col), colSizes[colsInLayout.get(col)]);
									for (RightAndMandatoryRow row : rowsInLayout.keySet()) {
										row.showRoleRight(col.getId());
									}
								}
							} else {
								if (selectedRoles.contains(col.getId())) {
									layoutHeader.setColumn(colsInLayout.get(col), 0);
									for (RightAndMandatoryRow row : rowsInLayout.keySet()) {
										row.hideRoleRight(col.getId());
									}
								}
							}
						}
						selectedRoles.clear();
						selectedRoles.addAll(selectedRolesNew);
						
						header.revalidate();
						header.repaint();
						
						sendWidthChanged();
						
						// save to preferences
						Preferences prefs = ClientPreferences.getInstance().getUserPreferences().node("collect")
								.node("entity").node(E.STATEMODEL.getEntityName());
						try {
							PreferencesUtils.putUIDList(prefs, PREFS_NODE_SELECTEDROLES, new ArrayList<UID>(selectedRoles));
						}
						catch(PreferencesException e1) {
							LOG.warn("actionPerformed: " + e1, e1);
						}
					}
				}
			});
			toolBar.add(roleSelection);
			help.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					(new Bubble(help, localeDelegate.getMessage(
							"StatePropertiesPanel.18", "<html>grau=nicht sichtbar<br/>gelb=lesen<br/>grÃ¼n=schreiben</html>"), 
							10, Bubble.Position.SE)).setVisible(true);
				}
			});
			toolBar.add(help);
			
			
			/**
			 * adjust splitpane
			 */
			sendWidthChanged();
			
			
			/**
			 * scroll to last position
			 */
			scrollRows.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
				@Override
				public void adjustmentValueChanged(AdjustmentEvent e) {
					scrollPosition = e.getValue();
				}
			});
			scrollRows.getVerticalScrollBar().setValues(scrollPosition!=-1?scrollPosition:0, CELL_HEIGHT, 0, 0);
			scrollRows.getVerticalScrollBar().setUnitIncrement(CELL_HEIGHT);
		}
		
		/**
		 * send width change to <code>actionListenerForWidthChanged</code>
		 */
		private void sendWidthChanged() {
			if (actionListenerForWidthChanged != null) {
				actionListenerForWidthChanged.actionPerformed(new ActionEvent(StateDependantRightsPanel.this, 0, "WIDTH_CHANGED"));
			}
		}
		
		/**
		 * 
		 * select roles to show in columns
		 */
		class RoleSelection extends SelectObjectsController<RightAndMandatoryColumnHeader> {

			public RoleSelection(Component parent) {
				super(parent, new DefaultSelectObjectsPanel<RightAndMandatoryColumnHeader>());
			}

		}
		
		/**
		 * 
		 * @param roleCount
		 * @return
		 */
		private double[] getColumns(int roleCount) {
			double[] result = new double[roleCount + 5];
			result[0] = 10;
			result[1] = TableLayout.PREFERRED;
			for (int i = 2; i < result.length; i++) {
				result[i] = CELL_WIDTH-1;
			}
			result[result.length-3] = 1; //last grid line on the right
			result[result.length-2] = CELL_WIDTH; //delete button
			result[result.length-1] = CELL_WIDTH; //scrollbar width
			
			return result;
		}
		
		/**
		 * 
		 * @param attributeGroupsSorted
		 * @param attributes
		 * @param subFormWithColumns
		 * @param chartDatasource 
		 * @return
		 */
		private double[] getRows(SortedMap<String, UID> attributeGroupsSorted, Map<UID, SortedSet<Attribute>> attributes, SortedMap<SubForm, SortedSet<Attribute>> subFormWithColumns, SortedSet<SubForm> chartDatasource) {
			int countGroups1 = 0;
			int countAttributes1 = 0;
			int countGroups2 = 0;
			int countAttributes2 = 0;
			int countGroups3 = 0;
			
			for (String group : attributeGroupsSorted.keySet()) {
				if (attributes.containsKey(attributeGroupsSorted.get(group))) {
					countGroups1++;
					countAttributes1 = countAttributes1 + attributes.get(attributeGroupsSorted.get(group)).size();
				}
			}
			for (SubForm subform : subFormWithColumns.keySet()) {
				countGroups2++;
				countAttributes2 = countAttributes2 + subFormWithColumns.get(subform).size();
			}
			countGroups3 = chartDatasource.size();
			
			int iCountRows = (countGroups1*2);
			iCountRows += countAttributes1;
			if (countGroups2 > 0) {
				iCountRows += 2/*=LINE_BREAK*/ + 1 /*=SubformLabel*/;
				iCountRows += (countGroups2*2) + countAttributes2;
			}
			if (countGroups3 > 0) {
				iCountRows += 2/*=LINE_BREAK*/ + 1 /*=SubformLabel*/;
				iCountRows += (countGroups3); // *2);
			}
			double[] result = new double[iCountRows];
			int i = 0;
			for (String group : attributeGroupsSorted.keySet()) {
				if (attributes.containsKey(attributeGroupsSorted.get(group))) {
					result[i] = CELL_HEIGHT;
					i++;
					
					for (Attribute efMeta : attributes.get(attributeGroupsSorted.get(group))) {
						result[i] = CELL_HEIGHT-1;
						i++;
					}
					
					result[i] = 1;
					i++;
				}
			}
			
			// Subforms
			if (countGroups2 > 0) {
				result[i] = GAP_LINEBREAK;
				i++;
				result[i] = CELL_HEIGHT;
				i++;
				result[i] = GAP_LINEBREAK;
				i++;
			}
			
			for (SubForm subform : subFormWithColumns.keySet()) {
				result[i] = CELL_HEIGHT;
				i++;
				
				for (Attribute efMeta : subFormWithColumns.get(subform)) {
					result[i] = CELL_HEIGHT-1;
					i++;
				}
				
				result[i] = 1;
				i++;
			}
			
			// Chart datasources
			if (countGroups3 > 0) {
				result[i] = GAP_LINEBREAK;
				i++;
				result[i] = CELL_HEIGHT;
				i++;
				result[i] = GAP_LINEBREAK;
				i++;
			}
			
			for (SubForm subform : chartDatasource) {
				result[i] = CELL_HEIGHT;
				i++;
			}
			
			return result;
		}
		
		private int addGroupToLayout(int iRow, RightAndMandatoryRow group, Map<RightAndMandatoryRow, Integer> rowsInLayout, Map<RightAndMandatoryRow, Set<RightAndMandatoryRow>> groupsWithArttributes, JPanel rows) {
			groupsWithArttributes.put(group, new HashSet<RightAndMandatoryRow>());
			rows.add(group.initView(100), "1," + iRow);
			rowsInLayout.put(group, iRow);
			iRow++;
			return iRow;
		}
		
		private int addAttributeToLayout(int iRow, RightAndMandatoryRow group, RightAndMandatoryRow attribute, Map<RightAndMandatoryRow, Integer> rowsInLayout, Map<RightAndMandatoryRow, Set<RightAndMandatoryRow>> groupsWithArttributes, JPanel rows) {
			groupsWithArttributes.get(group).add(attribute);
			rows.add(attribute.initView(100), "1," + iRow);
			rowsInLayout.put(attribute, iRow);
			iRow++;
			return iRow;
		}

		private RightAndMandatoryRow getRow(UID groupId, boolean expandable, UID attributeId, String name, boolean metaMandatory, StateVO statevo,
											boolean nullRightAllowed, List<UID> sortOrder, RightAndMandatoryRow group, ChangeListener detailsChangedListener) {
			boolean mandatory = false;
			boolean rightsEnabled = false;

			if (!metaMandatory) {
				for (MandatoryFieldVO mandatoryField : statevo.getMandatoryFields()) {
					if (mandatoryField.getField().equals(attributeId))
						mandatory = true;
				}
				if (group != null) {
					for (MandatoryColumnVO mandatoryColumn : statevo.getMandatoryColumns()) {
						if (mandatoryColumn.getColumn().equals(attributeId)) {
							mandatory = true;
						}
					}
				}
			}

			Map<UID, Object> roleRights = new HashMap<>();
			for (UID role : sortOrder) {
				roleRights.put(role, null); // default is no rights
			}

			if (groupId != null) {
				rightsEnabled = true;
				for (UID roleId : statevo.getUserRights().asMap().keySet()) {
					for (AttributegroupPermissionVO agPerm : statevo.getUserRights().asMap().get(roleId)) {
						if (agPerm.getAttributegroupUID().equals(groupId)) {
							roleRights.put(roleId, Boolean.valueOf(agPerm.isWritable()));
						}
					}
				}
			}

			RightAndMandatoryRow result = new RightAndMandatoryRow(groupId==null ? attributeId : groupId,
					name, rightsEnabled, roleRights, nullRightAllowed, mandatory, metaMandatory, sortOrder, group, false, expandable, detailsChangedListener);

			return result;
		}

		private RightAndMandatoryRow getSubRow(UID subformId, boolean expandable, UID groupId, String name, boolean metaMandatory, StateVO statevo,
											boolean nullRightAllowed, List<UID> sortOrder, RightAndMandatoryRow group, ChangeListener detailsChangedListener) {
			boolean mandatory = false;
			boolean rightsEnabled = groupId == null;

			Map<UID, Object> roleRights = new HashMap<>();
			for (UID role : sortOrder) {
				roleRights.put(role, groupId == null ? 0 : null); // default is no rights
			}

			for (UID roleId : statevo.getUserSubformRights().asMap().keySet()) {
				for (SubformPermissionVO sfPerm : statevo.getUserSubformRights().asMap().get(roleId)) {
					if (sfPerm.getSubform().equals(subformId)) {
						if (groupId == null) {
							roleRights.put(roleId, (sfPerm.canCreate() ? 1 : 0) + (sfPerm.canDelete() ? 2 : 0));
						}
						else {
							for (SubformGroupPermissionVO sfgroupPerm : sfPerm.getGroupPermissions()) {
								UID sfgroup = sfgroupPerm.getGroup();
								if (sfgroup == null) {
									sfgroup = UID.UID_NULL;
								}
								if (LangUtils.equal(sfgroup, groupId)) {
									rightsEnabled = true;
									roleRights.put(roleId, new Boolean(sfgroupPerm.isWriteable()));
								}
							}
						}

					}
				}
			}

			RightAndMandatoryRow result = new RightAndMandatoryRow(groupId != null ? groupId : subformId,
					name, rightsEnabled, roleRights, nullRightAllowed, mandatory, metaMandatory, sortOrder, group, true, expandable, detailsChangedListener);
			
			return result;
		}
		
		private int adjustRowHeaderWidth(Set<RightAndMandatoryRow> rows) {
			int max = 0;
			for (RightAndMandatoryRow row : rows) {
				if (max < row.getRowHeaderWidth())
					max = row.getRowHeaderWidth();
			}
			
			for (RightAndMandatoryRow row : rows) {
				row.updateView(max);
			}
			
			return max;
		}
		
		private int adjustColumnHeaderHeight(Collection<RightAndMandatoryColumnHeader> cols) {
			int max = 0;
			for (RightAndMandatoryColumnHeader col : cols) {
				if (max < col.getColumnNameHeight())
					max = col.getColumnNameHeight();
			}
			if (max > COLUMN_HEADER_HEIGHT_MAX) {
				max = COLUMN_HEADER_HEIGHT_MAX;
			}
			
			for (RightAndMandatoryColumnHeader col : cols) {
				col.updateView(max);
			}
			
			return max;
		}
		
		private Set<UID> getModules(List<CollectableEntityObject<UID>> usages) {
			Set<UID> result = new HashSet<UID>();
			for (CollectableEntityObject<UID> usage : usages)
				if (usage.getValueId(E.STATEMODELUSAGE.nuclos_module.getUID()) != null) {					
					UID lId = (UID)usage.getValueId(E.STATEMODELUSAGE.nuclos_module.getUID());
					result.add(lId);
				}
			return result;
		}
		
		private SortedMap<String, UID> getRolesSorted(Collection<MasterDataVO<?>> roles) {
			SortedMap<String, UID> result = new TreeMap<String, UID>();
			for (MasterDataVO<?> roleVO : roles) {
				result.put(roleVO.getFieldValue(E.ROLE.name.getUID(), String.class), (UID)roleVO.getPrimaryKey());
			}			
			return result;
		}
		
		private List<UID> getRolesSorted(SortedMap<String, UID> sortedRoles) {
			List<UID> result = new ArrayList<UID>();
			for (String role : sortedRoles.keySet()) {
				result.add(sortedRoles.get(role));
			}			
			return result;
		}
		
		private Map<UID, SortedSet<Attribute>> getAttributes(Set<UID> modules, Set<UID> metaMandatory) {
			Map<UID, SortedSet<Attribute>> result = new HashMap<UID, SortedSet<Attribute>>();
			
			for (UID module : modules) {				
				for (FieldMeta<?> efMeta : MetaProvider.getInstance().getAllEntityFieldsByEntity(module).values()) {
					UID groupId = efMeta.getFieldGroup();
					if (groupId == null) groupId = UID.UID_NULL;
					
					if (!result.containsKey(groupId)) {
						result.put(groupId, new TreeSet<Attribute>());
					}
					
					result.get(groupId).add(new Attribute(efMeta));
					
					if (!efMeta.isNullable())
						metaMandatory.add(efMeta.getUID());
				}
			}
			
			return result;
		}
		
		private SortedMap<SubForm, SortedSet<Attribute>> getSubForms(Set<UID> modules, Set<UID> metaMandatory) {
			final Map<UID, SubForm> entitySubForm = new HashMap<UID, SubForm>();
			final SortedMap<SubForm, SortedSet<Attribute>> result = new TreeMap<SubForm, SortedSet<Attribute>>();
			for (UID module : modules) {
				try {
					for (EntityAndField eafn : GenericObjectLayoutCache.getInstance().getSubFormEntities(module)) {
						if (!entitySubForm.containsKey(eafn.getEntity())) {
							entitySubForm.put(eafn.getEntity(), new SubForm(MetaProvider.getInstance().getEntity(eafn.getEntity())));
						}
						
						final SubForm sf = entitySubForm.get(eafn.getEntity());
						if (!result.containsKey(sf)) {
							result.put(sf, new TreeSet<Attribute>());
						}
						
						final EntityMeta<?> entity = MetaProvider.getInstance().getEntity(eafn.getEntity());
						if (entity.isDatasourceBased()) {
							continue; // no dynamic entity columns here
						}

						Collection<UID> groups = new HashSet<UID>();

						for (FieldMeta<?> efMeta : entity.getFields()) {
							if (efMeta.isDynamic()) {
								continue; // no dynamic entity columns here
							}

							UID group = efMeta.getFieldGroup();

							if (!SF.GROUP_UID_READ.equals(group) && !SF.GROUP_UID_WRITE.equals(group)) {
								groups.add(group);
							} else {
								continue;
							}

							if (!efMeta.getUID().equals(eafn.getField())) {
								// NUCLOS-4565 This function is disabled, no rights for single columns
								// result.get(sf).add(new Attribute(efMeta));
							}

							if (!efMeta.isNullable()) {
								metaMandatory.add(efMeta.getUID());
							}
						}

						for (UID group : groups) {
							for (MasterDataVO<?> groupVO : MasterDataCache.getInstance().get(E.ENTITYFIELDGROUP.getUID())) {
								if (groupVO.getPrimaryKey().equals(group)) {
									result.get(sf).add(new Attribute(group, groupVO.getFieldValue(E.ENTITYFIELDGROUP.name), true));
								} else if (group == null) {
									result.get(sf).add(new Attribute(UID.UID_NULL, "[Default]", true));
								}
							}
						}
					}
				} catch (Exception e) {
					LOG.warn("Error occured in getting subforms.", e);
				}
			}
			
			return result;
		}
		
		@SuppressWarnings("deprecation")
		private SortedSet<SubForm> getChartDatasources(Set<UID> modules) {
			final TreeSet<SubForm> result = new TreeSet<SubForm>();
			for (UID module : modules) {
				try {
					for (FieldMeta<?> fieldMeta : MetaProvider.getInstance().getAllReferencingFields(module)) {
						EntityMeta<?> refEntity = MetaProvider.getInstance().getEntity(fieldMeta.getEntity());
						if (refEntity.isChart()) {
							result.add(new SubForm(refEntity));
						}
					}
				} catch (Exception e) {
					LOG.warn("Error occured in getting chart datasources.", e);
				}
			}
			
			return result;
		}
		
		private Map<UID, String> getAttributeGroups(Collection<UID> groups) throws CommonBusinessException {
			Map<UID, String> result = new HashMap<UID, String>();
			
			for (MasterDataVO<?> groupVO : MasterDataCache.getInstance().get(E.ENTITYFIELDGROUP.getUID())) {
				UID groupId = (UID) groupVO.getPrimaryKey();
				if (groups.contains(groupId)) {
					result.put(groupId, groupVO.getFieldValue(E.ENTITYFIELDGROUP.name.getUID(), String.class));
				}
			}
			
			result.put(UID.UID_NULL, "[" + getSpringLocaleDelegate().getMessage("StatePropertiesPanel.19", "Ohne Gruppe") + "]");
			
			return result;
		}
		
		private SortedMap<String, UID> getAttributeGroupsSorted(Map<UID, String> attributeGroups) {
			SortedMap<String, UID> result = new TreeMap<String, UID>(); 
			for (Entry<UID, String> group : attributeGroups.entrySet()) {
				result.put(group.getValue(), group.getKey());
			}
			return result;
		}

		public ActionListener getActionListenerForWidthChanged() {
			return actionListenerForWidthChanged;
		}

		public void setActionListenerForWidthChanged(ActionListener actionListenerForWidthChanged) {
			/*
			 * Temporary deactivated...
			 * TODO remove if not in user any more
			 */
			//this.actionListenerForWidthChanged = actionListenerForWidthChanged;
		}
		
	}	// inner class StateDependantRightsPanel
	
	/**
	 * 
	 * for sorting...
	 */
	private static class Attribute implements Comparable<Attribute> {

		private final UID uid;
		private final String name;
		private final boolean bGroup;
		
		private Attribute(FieldMeta efMeta) {
			this(efMeta.getUID(), efMeta.getFieldName(), false);
		}

		private Attribute(UID uid, String name, boolean bGroup) {
			super();
			this.uid = uid;
			this.name = name;
			this.bGroup = bGroup;
		}

		private UID getUID() {
			return uid;
		}

		private String getName() {
			return name;
		}

		@Override
		public int compareTo(Attribute o) {
			return getName().compareTo(o.getName());
		}
		
	}
	
	/**
	 * 
	 * for sorting...
	 */
	private static class SubForm implements Comparable<SubForm> {
		
		final EntityMeta<?> eMeta;

		public SubForm(EntityMeta<?> eMeta) {
			super();
			this.eMeta = eMeta;
		}

		public EntityMeta<?> getMetaData() {
			return eMeta;
		}

		@Override
		public int compareTo(SubForm o) {			
			return eMeta.getEntityName().compareToIgnoreCase(o.getMetaData().getEntityName());
		}		
		
	}
	
	public static class HorizontalLabel extends JLabel {
		
		private boolean needsRotate;
		
		public HorizontalLabel(String text) {
			super(text);
		}
		
		@Override
		public Dimension getPreferredSize() {
			/*if (!needsRotate) {
			    return super.getPreferredSize();
			}*/
			
			Dimension size = super.getPreferredSize();
			return new Dimension(size.height, size.width);
		}

		@Override
		public Dimension getSize() {
			if (!needsRotate) {
				return super.getSize();
			}
			
			Dimension size = super.getSize();
			return new Dimension(size.height, size.width);
		}

		@Override
		public int getHeight() {
			return getSize().height;
		}

		@Override
		public int getWidth() {
			return getSize().width;
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D gr = (Graphics2D) g.create();

			gr.transform(AffineTransform.getQuadrantRotateInstance(1));
			gr.translate(0, -getSize().getWidth());

			needsRotate = true;
			super.paintComponent(gr);
			needsRotate = false;
		}
		
	}

	private final StatePropertiesPanelModel model = new StatePropertiesPanelModel();

	private final StateDependantRightsPanel pnlStateDependantRights = new StateDependantRightsPanel();
	
	// former Spring injection
	
	private SpringLocaleDelegate localeDelegate;
	
	// end of former Spring injection

	public StatePropertiesPanel() {
		super(new BorderLayout());
		setSpringLocaleDelegate(SpringApplicationContextHolder.getBean(SpringLocaleDelegate.class));

		final JTabbedPane tabpn = new JTabbedPane();
		this.add(tabpn, BorderLayout.CENTER);
		tabpn.addTab(SpringLocaleDelegate.getInstance().getMessage(
				"StatePropertiesPanel.6","Eigenschaften"), newStateBasicPropertiesPanel());
		tabpn.addTab(SpringLocaleDelegate.getInstance().getMessage(
				"StatePropertiesPanel.9","Berechtigungen"), pnlStateDependantRights);		
	}
	
	final void setSpringLocaleDelegate(SpringLocaleDelegate cld) {
		this.localeDelegate = cld;
	}

	final SpringLocaleDelegate getSpringLocaleDelegate() {
		return localeDelegate;
	}

	/**
	 * @return a new panel containing the basic properties for a state.
	 */
	private JPanel newStateBasicPropertiesPanel() {
		final SpringLocaleDelegate localeDelegate = getSpringLocaleDelegate();
		
		final JPanel pnlStateProperties = new JPanel(new GridBagLayout());
		pnlStateProperties.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		final JLabel labNameDE = new JLabel(localeDelegate.getMessage("StatePropertiesPanel.7","Name") + " (" + 
				localeDelegate.getMessage("StatePropertiesPanel.21","DE") + ")");
		final JTextField tfNameDE = new JTextField();
		labNameDE.setAlignmentY((float) 0.0);
		labNameDE.setHorizontalAlignment(SwingConstants.LEADING);
		labNameDE.setHorizontalTextPosition(SwingConstants.TRAILING);
		labNameDE.setLabelFor(tfNameDE);
		labNameDE.setVerticalAlignment(SwingConstants.CENTER);
		labNameDE.setVerticalTextPosition(SwingConstants.CENTER);
		tfNameDE.setAlignmentX((float) 0.0);
		tfNameDE.setAlignmentY((float) 0.0);
		tfNameDE.setPreferredSize(new Dimension(100, 21));
		tfNameDE.setDocument(model.docNameDE);
		tfNameDE.setEnabled(SecurityCache.getInstance().isWriteAllowedForMasterData(E.STATEMODEL.getUID()));
		
		final JLabel labNameEN = new JLabel(localeDelegate.getMessage("StatePropertiesPanel.7","Name") + " (EN)");
		final JTextField tfNameEN = new JTextField();
		labNameEN.setAlignmentY((float) 0.0);
		labNameEN.setHorizontalAlignment(SwingConstants.LEADING);
		labNameEN.setHorizontalTextPosition(SwingConstants.TRAILING);
		labNameEN.setLabelFor(tfNameEN);
		labNameEN.setVerticalAlignment(SwingConstants.CENTER);
		labNameEN.setVerticalTextPosition(SwingConstants.CENTER);
		
		tfNameEN.setAlignmentX((float) 0.0);
		tfNameEN.setAlignmentY((float) 0.0);
		tfNameEN.setPreferredSize(new Dimension(100, 21));
		tfNameEN.setDocument(model.docNameEN);
		tfNameEN.setEnabled(SecurityCache.getInstance().isWriteAllowedForMasterData(E.STATEMODEL.getUID()));

		final JLabel labMnemonic = new JLabel(localeDelegate.getMessage("StatePropertiesPanel.8","Numeral"));
		final JTextField tfMnemonic = new JTextField();
		labMnemonic.setAlignmentY((float) 0.0);
		labMnemonic.setHorizontalAlignment(SwingConstants.LEADING);
		labMnemonic.setHorizontalTextPosition(SwingConstants.TRAILING);
		labMnemonic.setLabelFor(tfMnemonic);
		labMnemonic.setVerticalAlignment(SwingConstants.CENTER);
		labMnemonic.setVerticalTextPosition(SwingConstants.CENTER);

		tfMnemonic.setAlignmentX((float) 0.0);
		tfMnemonic.setAlignmentY((float) 0.0);
		tfMnemonic.setPreferredSize(new Dimension(100, 21));
		tfMnemonic.setText("");
		tfMnemonic.setDocument(model.docMnemonic);
		tfMnemonic.setEnabled(SecurityCache.getInstance().isWriteAllowedForMasterData(E.STATEMODEL.getUID()));

		final JLabel labIcon = new JLabel(localeDelegate.getMessage("StatePropertiesPanel.20","Statusicon"));
		final JComponent tfIcon = model.clctImage.getImageLabel();
		model.clctImage.getLabeledComponent().setSize(new Dimension(100, 21));
		model.clctImage.setInputWidth(16);
		model.clctImage.setInputHeight(16);
		model.clctImage.clear();
		
		labIcon.setAlignmentY((float) 0.0);
		labIcon.setHorizontalAlignment(SwingConstants.LEADING);
		labIcon.setHorizontalTextPosition(SwingConstants.TRAILING);
		labIcon.setLabelFor(tfMnemonic);
		labIcon.setVerticalAlignment(SwingConstants.CENTER);
		labIcon.setVerticalTextPosition(SwingConstants.CENTER);

		tfIcon.setAlignmentX((float) 0.0);
		tfIcon.setAlignmentY((float) 0.0);
		tfIcon.setPreferredSize(new Dimension(100, 21));
		tfIcon.setEnabled(SecurityCache.getInstance().isWriteAllowedForMasterData(E.STATEMODEL.getUID()));
		
		final ResourceIconChooser tfIconSet = new ResourceIconChooser();
		tfIconSet.addDoubleClickAction(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					ImageIcon icon = (ImageIcon) tfIconSet.list.getSelectedValue();
					if (icon != null) {
						model.clctImage.loadImageFromIcon(icon);
					}
				}
			}
		});
		
		tfIconSet.setAlignmentX((float) 0.0);
		tfIconSet.setAlignmentY((float) 0.0);
		tfIconSet.setSize(new Dimension(100, 230));
		tfIconSet.setPreferredSize(new Dimension(100, 230));
		tfIconSet.setEnabled(SecurityCache.getInstance().isWriteAllowedForMasterData(E.STATEMODEL.getUID()));
		
		final JLabel labButtonLabelDE = new JLabel(localeDelegate.getMessage("nuclos.entityfield.state.buttonres.label","Button Beschriftung") + " (DE)");
		final JTextField tfButtonLabelDE = new JTextField();
		labButtonLabelDE.setAlignmentY((float) 0.0);
		labButtonLabelDE.setHorizontalAlignment(SwingConstants.LEADING);
		labButtonLabelDE.setHorizontalTextPosition(SwingConstants.TRAILING);
		labButtonLabelDE.setLabelFor(tfButtonLabelDE);
		labButtonLabelDE.setVerticalAlignment(SwingConstants.CENTER);
		labButtonLabelDE.setVerticalTextPosition(SwingConstants.CENTER);

		tfButtonLabelDE.setAlignmentX((float) 0.0);
		tfButtonLabelDE.setAlignmentY((float) 0.0);
		tfButtonLabelDE.setPreferredSize(new Dimension(100, 21));
		tfButtonLabelDE.setDocument(model.docButtonLabelDE);
		tfButtonLabelDE.setEnabled(SecurityCache.getInstance().isWriteAllowedForMasterData(E.STATEMODEL.getUID()));
		
		final JLabel labButtonLabelEN = new JLabel(localeDelegate.getMessage("nuclos.entityfield.state.buttonres.label","Button Beschriftung") + " (EN)");
		final JTextField tfButtonLabelEN = new JTextField();
		labButtonLabelEN.setAlignmentY((float) 0.0);
		labButtonLabelEN.setHorizontalAlignment(SwingConstants.LEADING);
		labButtonLabelEN.setHorizontalTextPosition(SwingConstants.TRAILING);
		labButtonLabelEN.setLabelFor(tfButtonLabelEN);
		labButtonLabelEN.setVerticalAlignment(SwingConstants.CENTER);
		labButtonLabelEN.setVerticalTextPosition(SwingConstants.CENTER);

		tfButtonLabelEN.setAlignmentX((float) 0.0);
		tfButtonLabelEN.setAlignmentY((float) 0.0);
		tfButtonLabelEN.setPreferredSize(new Dimension(100, 21));
		tfButtonLabelEN.setDocument(model.docButtonLabelEN);
		tfButtonLabelEN.setEnabled(SecurityCache.getInstance().isWriteAllowedForMasterData(E.STATEMODEL.getUID()));
		
		final org.nuclos.client.ui.resource.ResourceIconChooser.Button risButtonIcon = model.clctButtonIcon.getJComponent();
		
		final ColorChooserButton ccbColor = model.clctColor.getJComponent();
		
		final JLabel labDescriptionDE = new JLabel(localeDelegate.getMessage("StatePropertiesPanel.5","Hinweistext") + 
				" (" + 
				localeDelegate.getMessage("StatePropertiesPanel.21","DE") + ")");
		final JTextArea taDescriptionDE = new JTextArea();
		labDescriptionDE.setAlignmentY((float) 0.0);
		labDescriptionDE.setHorizontalAlignment(SwingConstants.LEADING);
		labDescriptionDE.setHorizontalTextPosition(SwingConstants.TRAILING);
		labDescriptionDE.setIconTextGap(4);
		labDescriptionDE.setLabelFor(taDescriptionDE);
		labDescriptionDE.setVerticalAlignment(SwingConstants.TOP);
		labDescriptionDE.setVerticalTextPosition(SwingConstants.TOP);

		taDescriptionDE.setAlignmentX((float) 0.0);
		taDescriptionDE.setAlignmentY((float) 0.0);
		taDescriptionDE.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
		taDescriptionDE.setText("");
		taDescriptionDE.setDocument(model.docDescriptionDE);
		taDescriptionDE.setFont(tfNameDE.getFont());
		taDescriptionDE.setLineWrap(true);
		taDescriptionDE.setEditable(SecurityCache.getInstance().isWriteAllowedForMasterData(E.STATEMODEL.getUID()));
		
		final JLabel labDescriptionEN = new JLabel(localeDelegate.getMessage("StatePropertiesPanel.5","Hinweistext")+ " (EN)");
		final JTextArea taDescriptionEN = new JTextArea();
		labDescriptionEN.setAlignmentY((float) 0.0);
		labDescriptionEN.setHorizontalAlignment(SwingConstants.LEADING);
		labDescriptionEN.setHorizontalTextPosition(SwingConstants.TRAILING);
		labDescriptionEN.setIconTextGap(4);
		labDescriptionEN.setLabelFor(taDescriptionEN);
		labDescriptionEN.setVerticalAlignment(SwingConstants.TOP);
		labDescriptionEN.setVerticalTextPosition(SwingConstants.TOP);
		
		taDescriptionEN.setAlignmentX((float) 0.0);
		taDescriptionEN.setAlignmentY((float) 0.0);
		taDescriptionEN.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
		taDescriptionEN.setText("");
		taDescriptionEN.setDocument(model.docDescriptionEN);
		taDescriptionEN.setFont(tfNameDE.getFont());
		taDescriptionEN.setLineWrap(true);
		taDescriptionEN.setEditable(SecurityCache.getInstance().isWriteAllowedForMasterData(E.STATEMODEL.getUID()));
		
		final JLabel labTabbedPaneName = new JLabel(localeDelegate.getMessage("StatePropertiesPanel.1","Aktive Tablasche"));
		final JComboBox cmbbxTabbedPaneName = new JComboBox();
		labTabbedPaneName.setHorizontalAlignment(SwingConstants.LEADING);
		labTabbedPaneName.setHorizontalTextPosition(SwingConstants.TRAILING);
		labTabbedPaneName.setIconTextGap(4);
		labTabbedPaneName.setLabelFor(cmbbxTabbedPaneName);
		labTabbedPaneName.setVerticalAlignment(SwingConstants.CENTER);

		cmbbxTabbedPaneName.setModel(model.modelTab);
		cmbbxTabbedPaneName.setPreferredSize(new Dimension(100, 21));
		cmbbxTabbedPaneName.setAlignmentY((float) 0.0);
		cmbbxTabbedPaneName.setAlignmentX((float) 0.0);

		pnlStateProperties.setMaximumSize(new Dimension(2147483647, 2147483647));

		final JScrollPane scrlpnDE = new JScrollPane(taDescriptionDE);
		scrlpnDE.setAutoscrolls(true);
		scrlpnDE.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		final JScrollPane scrlpnEN = new JScrollPane(taDescriptionEN);
		scrlpnEN.setAutoscrolls(true);
		scrlpnEN.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		
		this.setAlignmentX((float) 0.0);
		this.setAlignmentY((float) 0.0);

		pnlStateProperties.add(labNameDE,
				new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
						new Insets(2, 0, 0, 5), 0, 0));
		pnlStateProperties.add(tfNameDE,
				new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
						new Insets(2, 5, 0, 0), 0, 0));
		pnlStateProperties.add(labNameEN,
				new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
						new Insets(2, 0, 0, 5), 0, 0));
		pnlStateProperties.add(tfNameEN,
				new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
						new Insets(2, 5, 0, 0), 0, 0));
		pnlStateProperties.add(labMnemonic,
				new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
						new Insets(2, 0, 0, 5), 0, 0));
		pnlStateProperties.add(tfMnemonic,
				new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
						new Insets(2, 5, 0, 0), 0, 0));
		pnlStateProperties.add(labIcon,
				new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
						new Insets(2, 0, 0, 5), 0, 0));
		pnlStateProperties.add(tfIcon,
				new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
						new Insets(2, 5, 0, 0), 0, 0));
		pnlStateProperties.add(tfIconSet,
				new GridBagConstraints(1, 5, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
						new Insets(2, 5, 0, 0), 0, 0));
		pnlStateProperties.add(labButtonLabelDE,
				new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
						new Insets(2, 0, 0, 5), 0, 0));
		pnlStateProperties.add(tfButtonLabelDE,
				new GridBagConstraints(1, 6, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
						new Insets(2, 5, 0, 0), 0, 0));
		pnlStateProperties.add(labButtonLabelEN,
				new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
						new Insets(2, 0, 0, 5), 0, 0));
		pnlStateProperties.add(tfButtonLabelEN,
				new GridBagConstraints(1, 7, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
						new Insets(2, 5, 0, 0), 0, 0));
		pnlStateProperties.add(risButtonIcon,
				new GridBagConstraints(1, 8, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
						new Insets(2, 5, 0, 0), 0, 0));
		pnlStateProperties.add(ccbColor,
				new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
						new Insets(2, 5, 0, 0), 0, 0));
		pnlStateProperties.add(labDescriptionDE,
				new GridBagConstraints(0, 10, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
						new Insets(2, 0, 0, 5), 0, 0));
		pnlStateProperties.add(scrlpnDE,
				new GridBagConstraints(1, 10, 2, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
						new Insets(2, 5, 0, 0), 0, 0));
		pnlStateProperties.add(labDescriptionEN,
				new GridBagConstraints(0, 11, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
						new Insets(2, 0, 0, 5), 0, 0));
		pnlStateProperties.add(scrlpnEN,
				new GridBagConstraints(1, 11, 2, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
						new Insets(2, 5, 0, 0), 0, 0));
		pnlStateProperties.add(labTabbedPaneName,
			new GridBagConstraints(0, 15, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
					new Insets(2, 0, 0, 5), 0, 0));
		pnlStateProperties.add(cmbbxTabbedPaneName,
			new GridBagConstraints(1, 15, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
					new Insets(2, 5, 0, 0), 0, 0));
		return pnlStateProperties;
	}

	public StateDependantRightsPanel getStateDependantRightsPanel() {
		return this.pnlStateDependantRights;
	}

	public StatePropertiesPanelModel getModel() {
		return model;
	}

}	// class StatePropertiesPanel
