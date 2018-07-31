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
package org.nuclos.client.ui.collect;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.Scrollable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumn;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.commons.lang.NullArgumentException;
import org.apache.log4j.Logger;
import org.jdesktop.jxlayer.JXLayer;
import org.jdesktop.jxlayer.plaf.ext.LockableUI;
import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.StandardXYSeriesLabelGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CategoryMarker;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.PlotState;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.plot.dial.DialBackground;
import org.jfree.chart.plot.dial.DialCap;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialTextAnnotation;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialRange;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.CategoryItemRendererState;
import org.jfree.chart.renderer.category.GradientBarPainter;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYStepRenderer;
import org.jfree.chart.util.ResourceBundleWrapper;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.general.ValueDataset;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYBarDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.layout.LCBLayout;
import org.jfree.text.G2TextMeasurer;
import org.jfree.text.TextBlock;
import org.jfree.text.TextBlockAnchor;
import org.jfree.text.TextUtilities;
import org.jfree.ui.FontChooserPanel;
import org.jfree.ui.FontDisplayField;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.Layer;
import org.jfree.ui.PaintSample;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.StandardGradientPaintTransformer;
import org.jfree.ui.StrokeChooserPanel;
import org.jfree.ui.StrokeSample;
import org.jfree.ui.TextAnchor;
import org.nuclos.client.common.FocusActionListener;
import org.nuclos.client.common.MetaProvider;
import org.nuclos.client.common.SearchConditionSubFormController.SearchConditionTableModelImpl;
import org.nuclos.client.datasource.DatasourceDelegate;
import org.nuclos.client.datasource.admin.ParameterPanel;
import org.nuclos.client.ui.Icons;
import org.nuclos.client.ui.UIUtils;
import org.nuclos.client.ui.collect.component.LookupListener;
import org.nuclos.client.ui.collect.model.CollectableTableModel;
import org.nuclos.client.ui.collect.subform.SubForm;
import org.nuclos.client.ui.event.IPopupListener;
import org.nuclos.common.NuclosFatalException;
import org.nuclos.common.RigidUtils;
import org.nuclos.common.UID;
import org.nuclos.common.collect.ChartRangeType;
import org.nuclos.common.collect.ChartUtils;
import org.nuclos.common.collect.IChart;
import org.nuclos.common.collect.collectable.CollectableEntity;
import org.nuclos.common.collect.collectable.CollectableEntityField;
import org.nuclos.common.collect.collectable.CollectableField;
import org.nuclos.common.collect.collectable.CollectableFieldFormat;
import org.nuclos.common.collect.collectable.CollectableFieldsProvider;
import org.nuclos.common.collect.collectable.DefaultCollectableEntityProvider;
import org.nuclos.common.collection.Pair;
import org.nuclos.common.collection.Transformer;
import org.nuclos.common.report.valueobject.DatasourceParameterVO;
import org.nuclos.common2.LangUtils;
import org.nuclos.common2.SpringLocaleDelegate;
import org.nuclos.common2.StringUtils;
import org.nuclos.common2.exception.CommonFatalException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import com.thoughtworks.xstream.XStream;

/**
 * Chart for displaying/editing dependant <code>Collectable</code>s.
 *
 * <br>Created by Novabit Informationssysteme GmbH
 * <br>Please visit <a href="http://www.novabit.de">www.novabit.de</a>
 */
@SuppressWarnings("serial")
public class Chart extends JPanel 
	implements IChart, ActionListener, Closeable {
	
	private static final Logger LOG = Logger.getLogger(Chart.class);
	
	{
		try {
			Field field = FontChooserPanel.class.getField("SIZES");
			field.setAccessible(true);
			
		    Field modifiersField = Field.class.getDeclaredField("modifiers");
		    modifiersField.setAccessible(true);
		    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		    field.set(null, new String[]{"7", "8", "9", "10", "11", "12", "14", "16",
		            "18", "20", "22", "24", "28", "36", "48", "72"});
		} catch (Exception e) {
			// do nothing.
			LOG.warn("non-static block failed: " + e, e);
		}
	}

	public static class ChartColumn {
		private final Class clazz;
		private final String property;
		private final String label;
		public ChartColumn(String property, String label, Class clazz) {
			this.property = property;
			this.label = label;
			this.clazz = clazz;
		}
		public Class getClazz() {
			return clazz;
		}
		public String getProperty() {
			return property;
		}
		public String getLabel() {
			return label;
		}
	}

	public static enum ChartFunction {
		BARCHART {
			@Override
			protected Dataset[] createDataset(Chart chart) {
				return createDataset(chart, "");
			}
			@Override
			protected Dataset[] createDataset(Chart chart, String prefix) {
				DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
				
				try {
					JTable tbl = chart.getSubForm().getJTable();
					
					TableColumn cValue = tbl.getColumn(chart.getProperty(prefix + PROPERTY_VALUE_COLUMN, UID.class));
					
					UID pDomainAxis = chart.getProperty(prefix + PROPERTY_DOMAIN_COLUMN, UID.class);
					TableColumn cDomainAxis = pDomainAxis == null ? null : tbl.getColumn(pDomainAxis);
					UID pRangeAxis = chart.getProperty(prefix + PROPERTY_RANGE_COLUMN, UID.class);
					TableColumn cRangeAxis = pRangeAxis == null ? null : tbl.getColumn(pRangeAxis);
					
					UID pItemWidth = chart.getProperty(prefix + PROPERTY_ITEMWIDTH_COLUMN, UID.class);
					TableColumn cItemWidth = pItemWidth == null ? null : tbl.getColumn(pItemWidth);
					UID pItemColor = chart.getProperty(prefix + PROPERTY_ITEMCOLOR_COLUMN, UID.class);
					TableColumn cItemColor = pItemColor == null ? null : tbl.getColumn(pItemColor);

					UID pDomainMarker = chart.getProperty(prefix + PROPERTY_DOMAIN_MARKER_COLUMN, UID.class);
					TableColumn cDomainMarker = pDomainMarker == null ? null : tbl.getColumn(pDomainMarker);
					UID pRangeMarker = chart.getProperty(prefix + PROPERTY_RANGE_MARKER_COLUMN, UID.class);
					TableColumn cRangeMarker = pRangeMarker == null ? null : tbl.getColumn(pRangeMarker);
					
					UID pStandardRange = chart.getProperty(prefix + PROPERTY_BARCHART_STANDARDRANGE_COLUMN, UID.class);
					TableColumn cStandardRange = pStandardRange == null ? null : tbl.getColumn(pStandardRange);
					
					if (tbl.getModel() instanceof SearchConditionTableModelImpl) {
						defaultcategorydataset.addValue(0, "", "");
						return new Dataset[]{defaultcategorydataset};
					}
					if (tbl.getColumnCount() > 0) {
						if (tbl.getRowCount() <= 0) {
							defaultcategorydataset.addValue(0, "", "");
						} else {
							for (int j = 0; j < tbl.getRowCount(); j++) {
								CollectableField vValue = (CollectableField)tbl.getValueAt(j, cValue.getModelIndex());
					
								CollectableField vDomainAxis = cDomainAxis == null ? null : (CollectableField)tbl.getValueAt(j, cDomainAxis.getModelIndex());
								CollectableField vRangeAxis = (CollectableField)tbl.getValueAt(j, cRangeAxis.getModelIndex());
								
								defaultcategorydataset.addValue(vValue == null || vValue.getValue() == null ? 0 : (Number)vValue.getValue(),
									vDomainAxis == null || vDomainAxis.getValue() == null ? "" : (Comparable)vDomainAxis.getValue(), vRangeAxis == null || vRangeAxis.getValue() == null ? "" : (Comparable)vRangeAxis.getValue());
								
								CollectableField vItemWidth= cItemWidth == null ? null : (CollectableField)tbl.getValueAt(j, cItemWidth.getModelIndex());
								chart.setProperty(prefix + PROPERTY_ITEMWIDTH_COLUMN_VALUE + ":" + defaultcategorydataset.getRowKeys().indexOf(
										vDomainAxis == null || vDomainAxis.getValue() == null ? "" : (Comparable)vDomainAxis.getValue()), vItemWidth == null || vItemWidth.getValue() == null ? "" : (Number)vItemWidth.getValue());
								CollectableField vItemColor= cItemColor == null ? null : (CollectableField)tbl.getValueAt(j, cItemColor.getModelIndex());
								chart.setProperty(prefix + PROPERTY_ITEMCOLOR_COLUMN_VALUE + ":" + defaultcategorydataset.getRowKeys().indexOf(
										vDomainAxis == null || vDomainAxis.getValue() == null ? "" : (Comparable)vDomainAxis.getValue()), vItemColor == null || vItemColor.getValue() == null ? "" : (String)vItemColor.getValue());

								CollectableField vDomainMarker= cDomainMarker == null ? null : (CollectableField)tbl.getValueAt(j, cDomainMarker.getModelIndex());
								chart.setProperty(prefix + PROPERTY_DOMAIN_MARKER_COLUMN_VALUE, vDomainMarker == null || vDomainMarker.getValue() == null ? "" : (String)vDomainMarker.getValue()); 
								CollectableField vRangeMarker= cRangeMarker == null ? null : (CollectableField)tbl.getValueAt(j, cRangeMarker.getModelIndex());
								chart.setProperty(prefix + PROPERTY_RANGE_MARKER_COLUMN_VALUE, vRangeMarker == null || vRangeMarker.getValue() == null ? "" : (Number)vRangeMarker.getValue());
								
								CollectableField vStandardRange = cStandardRange == null ? null : (CollectableField)tbl.getValueAt(j, cStandardRange.getModelIndex());
								chart.setProperty(prefix + PROPERTY_BARCHART_STANDARDRANGE_COLUMN_VALUE + ":" + defaultcategorydataset.getRowKeys().indexOf(
										vDomainAxis == null || vDomainAxis.getValue() == null ? "" : (Comparable)vDomainAxis.getValue()), vStandardRange == null || vStandardRange.getValue() == null ? "" : (String)vStandardRange.getValue()); 
							}
						}
					}
				} catch (Exception e) {
					LOG.warn(e.getMessage(), e);
				}
				
				return new Dataset[]{defaultcategorydataset};
	        }
			@Override
			public JFreeChart createChart(final Chart chart) {
				CategoryDataset dataset = (CategoryDataset)createDataset(chart)[0];
				
				JFreeChart jfreechart = ChartFactory.createBarChart("", "", "", dataset, PlotOrientation.VERTICAL, true, false, false);
				jfreechart.getPlot().setDrawingSupplier(new DefaultDrawingSupplier());
				ChartUtilities.applyCurrentTheme(jfreechart);
			    
				setCommonChartProperties(chart, jfreechart);

				if (!(jfreechart.getPlot() instanceof CategoryPlot)) {
					return jfreechart;
				}
				
				BarRenderer renderer = new BarRenderer() {
					private Map<Integer, Double> barWidths = new HashMap<Integer, Double>();
					@Override
					public void setSeriesStroke(int series, Stroke stroke) {
						if (stroke instanceof BasicStroke) {
							barWidths.put(Integer.valueOf(series), new Double(((BasicStroke)stroke).getLineWidth()));
						}
					};
					@Override
					public void drawItem(Graphics2D g2, CategoryItemRendererState state, final Rectangle2D dataArea, 
							final CategoryPlot plot, CategoryAxis domainAxis, final ValueAxis rangeAxis, CategoryDataset dataset, int row, int column, int pass) {
						final Double barWidth = state.getBarWidth();
						if (barWidths.containsKey(Integer.valueOf(row))) {
							state.setBarWidth(barWidths.get(Integer.valueOf(row)));
						}
						super.drawItem(g2, state, dataArea, plot, domainAxis, rangeAxis, dataset, row, column, pass);
						
						state.setBarWidth(barWidth);
						
						setBarPainter(new GradientBarPainter() {
							@Override
						    public void paintBar(Graphics2D g2, BarRenderer renderer, int row, int column, RectangularShape bar, RectangleEdge base) {
						        Paint itemPaint = renderer.getItemPaint(row, column);

						        Color c0, c1;
						        if (itemPaint instanceof Color) {
						            c0 = (Color) itemPaint;
						            c1 = c0.brighter();
						        } else if (itemPaint instanceof GradientPaint) {
						            GradientPaint gp = (GradientPaint) itemPaint;
						            c0 = gp.getColor1();
						            c1 = gp.getColor2();
						        } else {
						            c0 = Color.blue;
						            c1 = Color.blue.brighter();
						        }

						        // as a special case, if the bar colour has alpha == 0, we draw
						        // nothing.
						        if (c0.getAlpha() == 0) {
						            return;
						        }

						        if (base == RectangleEdge.TOP || base == RectangleEdge.BOTTOM) {
						        	double g1x = 0.10;
							        double g2x = 0.20;
							        double g3x = 0.80;
						            Rectangle2D[] regions = splitVerticalBar(bar, g1x, g2x,
						                    g3x);
						            GradientPaint gp = new GradientPaint((float) regions[0].getMinX(),
						                    0.0f, c0, (float) regions[0].getMaxX(), 0.0f, Color.white);
						            g2.setPaint(gp);
						            g2.fill(regions[0]);

						            gp = new GradientPaint((float) regions[1].getMinX(), 0.0f,
						                    Color.white, (float) regions[1].getMaxX(), 0.0f, c0);
						            g2.setPaint(gp);
						            g2.fill(regions[1]);

						            gp = new GradientPaint((float) regions[2].getMinX(), 0.0f, c0,
						                    (float) regions[2].getMaxX(), 0.0f, c1);
						            g2.setPaint(gp);
						            g2.fill(regions[2]);

						            gp = new GradientPaint((float) regions[3].getMinX(), 0.0f, c1,
						                     (float) regions[3].getMaxX(), 0.0f, c0);
						            g2.setPaint(gp);
						            g2.fill(regions[3]);
						        } else if (base == RectangleEdge.LEFT || base == RectangleEdge.RIGHT) {
						        	double g1x = 0.10;
							        double g2x = 0.20;
							        double g3x = 0.80;
						            Rectangle2D[] regions = splitHorizontalBar(bar, g1x, g2x,
						                    g3x);
						            GradientPaint gp = new GradientPaint(0.0f,
						                    (float) regions[0].getMinY(), c0, 0.0f,
						                    (float) regions[0].getMaxX(), Color.white);
						            g2.setPaint(gp);
						            g2.fill(regions[0]);

						            gp = new GradientPaint(0.0f, (float) regions[1].getMinY(),
						                    Color.white, 0.0f, (float) regions[1].getMaxY(), c0);
						            g2.setPaint(gp);
						            g2.fill(regions[1]);

						            gp = new GradientPaint(0.0f, (float) regions[2].getMinY(), c0,
						                    0.0f, (float) regions[2].getMaxY(), c1);
						            g2.setPaint(gp);
						            g2.fill(regions[2]);

						            gp = new GradientPaint(0.0f, (float) regions[3].getMinY(), c1,
						                     0.0f, (float) regions[3].getMaxY(), c0);
						            g2.setPaint(gp);
						            g2.fill(regions[3]);
						        }
						        
						        String cStandardRange = chart.getProperty(PROPERTY_BARCHART_STANDARDRANGE_COLUMN_VALUE + ":" + column, String.class);
								if (cStandardRange != null && cStandardRange.indexOf(";") != -1) {
									final RectangleEdge edge = plot.getRangeAxisEdge();
									final String[] args = cStandardRange.split(";");
									for (String arg : args) {
										try {
											final double val = Double.parseDouble(arg);
											final double value = rangeAxis.valueToJava2D(val, dataArea, edge);
											
											final Font of = g2.getFont();
											final Paint op = g2.getPaint();
											final Stroke os = g2.getStroke();

											final RectangularShape rs;
											if (plot.getOrientation() == PlotOrientation.VERTICAL) {
												rs = new Rectangle2D.Double(bar.getMinX(), value-10, bar.getWidth()+15, 1);
											} else {
												rs = new Rectangle2D.Double(value, bar.getMinY()-(bar.getMaxY()-bar.getMinY()), 1, (bar.getMaxY()-bar.getMinY())+10);
											}
											g2.setStroke(new BasicStroke());
									        g2.setPaint(Color.LIGHT_GRAY);
									        g2.draw(rs);
									        
									        g2.setPaint(Color.DARK_GRAY);
									        g2.setFont(new Font("Dialog", 0, 11));
									        if (plot.getOrientation() == PlotOrientation.VERTICAL) {
									        	g2.drawString(NumberFormat.getInstance().format(val), (float)(bar.getMinX()+rs.getWidth()-8), (float)(value-12));
											} else {
												g2.drawString(NumberFormat.getInstance().format(val), (float)(rs.getX()+rs.getWidth()+2), (float)((rs.getY()+rs.getHeight())-(bar.getMaxY()-bar.getMinY())));
											}
									        
									        g2.setStroke(os);
									        g2.setPaint(op);
									        g2.setFont(of);
										} catch (Exception e) {
											// ignore.
										}
									}
								}
														        
						        // draw the outline...
						        if (renderer.isDrawBarOutline()
						            /*&& state.getBarWidth() > renderer.BAR_OUTLINE_WIDTH_THRESHOLD*/) {
						            Stroke stroke = renderer.getItemOutlineStroke(row, column);
						            Paint paint = renderer.getItemOutlinePaint(row, column);
						            if (stroke != null && paint != null) {
						                g2.setStroke(stroke);
						                g2.setPaint(paint);
						                g2.draw(bar);
						            }
						        }
						    }	
							
						    private Rectangle2D[] splitVerticalBar(RectangularShape bar, double a, double b, double c) {
						        Rectangle2D[] result = new Rectangle2D[4];
						        double x0 = bar.getMinX() - 1;
						        double x1 = Math.rint(x0 + (bar.getWidth() * a));
						        double x2 = Math.rint(x0 + (bar.getWidth() * b));
						        double x3 = Math.rint(x0 + (bar.getWidth() * c));
						        result[0] = new Rectangle2D.Double(bar.getMinX(), bar.getMinY(),
						                x1 - x0, bar.getHeight());
						        result[1] = new Rectangle2D.Double(x1, bar.getMinY(), x2 - x1,
						                bar.getHeight());
						        result[2] = new Rectangle2D.Double(x2 - 1, bar.getMinY(), x3 - x2 + 2,
						                bar.getHeight());
						        result[3] = new Rectangle2D.Double(x3, bar.getMinY(),
						                bar.getMaxX() - x3, bar.getHeight());
						        return result;
						    }
					        private Rectangle2D[] splitHorizontalBar(RectangularShape bar, double a, double b, double c) {
					            Rectangle2D[] result = new Rectangle2D[4];
					            double y0 = bar.getMinY();
					            double y1 = Math.rint(y0 + (bar.getHeight() * a));
					            double y2 = Math.rint(y0 + (bar.getHeight() * b));
					            double y3 = Math.rint(y0 + (bar.getHeight() * c));
					            result[0] = new Rectangle2D.Double(bar.getMinX(), bar.getMinY(),
					                    bar.getWidth(), y1 - y0);
					            result[1] = new Rectangle2D.Double(bar.getMinX(), y1, bar.getWidth(),
					                    y2 - y1);
					            result[2] = new Rectangle2D.Double(bar.getMinX(), y2, bar.getWidth(),
					                    y3 - y2);
					            result[3] = new Rectangle2D.Double(bar.getMinX(), y3, bar.getWidth(),
					                    bar.getMaxY() - y3);
					            return result;
					        }
						});
					}
				};
				
				((CategoryPlot)jfreechart.getPlot()).setRenderer(0, renderer);
			
				// set properties for category plot
				((CategoryPlot)jfreechart.getPlot()).setOrientation(
						chart.getProperty(PROPERTY_PLOT_ORIENTATION, String.class, PlotOrientation.HORIZONTAL.toString())
							.equals(PlotOrientation.VERTICAL.toString()) ? PlotOrientation.VERTICAL : PlotOrientation.HORIZONTAL);
				// set properties for domain axis
				((CategoryPlot)jfreechart.getPlot()).getDomainAxis().setLabel(chart.getProperty(PROPERTY_DOMAIN_LABEL, String.class));
				if (chart.getProperty(PROPERTY_DOMAIN_LABEL_FONT, Font.class) != null) {
					((CategoryPlot)jfreechart.getPlot()).getDomainAxis().setLabelFont(chart.getProperty(PROPERTY_DOMAIN_LABEL_FONT, Font.class));
				}
				((CategoryPlot)jfreechart.getPlot()).getDomainAxis().setLabelPaint(chart.getProperty(PROPERTY_DOMAIN_LABEL_COLOR, Color.class, Color.BLACK));
				if (chart.getProperty(PROPERTY_DOMAIN_TICK_LABELS_FONT, Font.class) != null) {
					((CategoryPlot)jfreechart.getPlot()).getDomainAxis().setTickLabelFont(chart.getProperty(PROPERTY_DOMAIN_TICK_LABELS_FONT, Font.class));
				}
				((CategoryPlot)jfreechart.getPlot()).getDomainAxis().setTickLabelsVisible(chart.getProperty(PROPERTY_DOMAIN_TICK_LABELS_VISIBLE, Boolean.class, Boolean.TRUE));
				((CategoryPlot)jfreechart.getPlot()).getDomainAxis().setTickMarksVisible(chart.getProperty(PROPERTY_DOMAIN_TICK_MARKS_VISIBLE, Boolean.class, Boolean.TRUE));
				// set properties for range axis
				((CategoryPlot)jfreechart.getPlot()).getRangeAxis().setLabel(chart.getProperty(PROPERTY_RANGE_LABEL, String.class));
				if (chart.getProperty(PROPERTY_RANGE_LABEL_FONT, Font.class) != null) {
					((CategoryPlot)jfreechart.getPlot()).getRangeAxis().setLabelFont(chart.getProperty(PROPERTY_RANGE_LABEL_FONT, Font.class));
				}
				((CategoryPlot)jfreechart.getPlot()).getRangeAxis().setLabelPaint(chart.getProperty(PROPERTY_RANGE_LABEL_COLOR, Color.class, Color.BLACK));
				if (chart.getProperty(PROPERTY_RANGE_TICK_LABELS_FONT, Font.class) != null) {
					((CategoryPlot)jfreechart.getPlot()).getRangeAxis().setTickLabelFont(chart.getProperty(PROPERTY_RANGE_TICK_LABELS_FONT, Font.class));
				}
				((CategoryPlot)jfreechart.getPlot()).getRangeAxis().setTickLabelsVisible(chart.getProperty(PROPERTY_RANGE_TICK_LABELS_VISIBLE, Boolean.class, Boolean.TRUE));
				((CategoryPlot)jfreechart.getPlot()).getRangeAxis().setTickMarksVisible(chart.getProperty(PROPERTY_RANGE_TICK_MARKS_VISIBLE, Boolean.class, Boolean.TRUE));
				((CategoryPlot)jfreechart.getPlot()).getRangeAxis().setAutoRange(chart.getProperty(PROPERTY_RANGE_AUTORANGE, Boolean.class, Boolean.TRUE));
				if (!((CategoryPlot)jfreechart.getPlot()).getRangeAxis().isAutoRange()) {
					((CategoryPlot)jfreechart.getPlot()).getRangeAxis().setLowerBound(chart.getProperty(PROPERTY_RANGE_LOWERBOUND, Double.class, 0D));
					((CategoryPlot)jfreechart.getPlot()).getRangeAxis().setUpperBound(chart.getProperty(PROPERTY_RANGE_UPPERBOUND, Double.class, 0D));
				}
				
				// set width and colors
				for (int j = 0; j < ((CategoryPlot)jfreechart.getPlot()).getDataset(0).getRowKeys().size(); j++) {
					Object cItemWidth = chart.getProperty(PROPERTY_ITEMWIDTH_COLUMN_VALUE + ":" + j, Number.class);
					if (cItemWidth instanceof Number) {
						if (((Number)cItemWidth).floatValue() > 0) {
							((AbstractRenderer)((CategoryPlot)jfreechart.getPlot()).getRenderer(0)).setSeriesStroke(j,
									new BasicStroke(((Number)cItemWidth).floatValue()));
						}
					}
					Color cItemColor = chart.getProperty(PROPERTY_ITEMCOLOR_COLUMN_VALUE + ":" + j, Color.class);
					if (cItemColor != null) {
						((AbstractRenderer)((CategoryPlot)jfreechart.getPlot()).getRenderer(0)).setSeriesPaint(j, cItemColor);
					}
				}
				
				// set markers.
				Comparable cDomainMarker = chart.getProperty(PROPERTY_DOMAIN_MARKER_COLUMN_VALUE, String.class);
				if (cDomainMarker instanceof Comparable) {
					CategoryMarker marker1 = new CategoryMarker(cDomainMarker, Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker1.setDrawAsLine(true);
					marker1.setLabel(chart.getProperty(PROPERTY_DOMAIN_MARKER_LABEL, String.class));
					marker1.setLabelFont(new Font("Dialog", 0, 11));
					marker1.setLabelPaint(Color.DARK_GRAY);
					if (((CategoryPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.VERTICAL) {
						marker1.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
					} else {
						marker1.setLabelAnchor(RectangleAnchor.RIGHT);
						marker1.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
					}
					marker1.setLabelOffset(new RectangleInsets(2D, 5D, 2D, 5D));
					((CategoryPlot)jfreechart.getPlot()).addDomainMarker(marker1, Layer.BACKGROUND);
					CategoryMarker marker2 = new CategoryMarker(cDomainMarker, Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker2.setDrawAsLine(true);
					marker2.setLabel("" + marker2.getKey());
					marker2.setLabelFont(new Font("Dialog", 0, 11));
					marker2.setLabelPaint(Color.DARK_GRAY);
					if (((CategoryPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.VERTICAL) {
						marker2.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(15D, 5D, 2D, 5D));
					} else {
						marker2.setLabelAnchor(RectangleAnchor.RIGHT);
						marker2.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(-25D, 5D, 2D, 5D));
					}
					((CategoryPlot)jfreechart.getPlot()).addDomainMarker(marker2, Layer.BACKGROUND);
				}
				Object cRangeMarker = chart.getProperty(PROPERTY_RANGE_MARKER_COLUMN_VALUE, Number.class);
				if (cRangeMarker instanceof Number) {
					ValueMarker marker1 = new ValueMarker(((Number)cRangeMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker1.setLabel(chart.getProperty(PROPERTY_RANGE_MARKER_LABEL, String.class));
					marker1.setLabelFont(new Font("Dialog", 0, 11));
					marker1.setLabelPaint(Color.DARK_GRAY);
					if (((CategoryPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.HORIZONTAL) {
						marker1.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
					} else {
						marker1.setLabelAnchor(RectangleAnchor.RIGHT);
						marker1.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
					}
					marker1.setLabelOffset(new RectangleInsets(2D, 5D, 2D, 5D));
					((CategoryPlot)jfreechart.getPlot()).addRangeMarker(marker1, Layer.BACKGROUND);
					ValueMarker marker2 = new ValueMarker(((Number)cRangeMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker2.setLabel("" + CollectableFieldFormat.getInstance(Double.class).format("#,##0.00", marker2.getValue()));
					marker2.setLabelFont(new Font("Dialog", 0, 11));
					marker2.setLabelPaint(Color.DARK_GRAY);	
					if (((CategoryPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.HORIZONTAL) {
						marker2.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(15D, 5D, 2D, 5D));
					} else {
						marker2.setLabelAnchor(RectangleAnchor.RIGHT);
						marker2.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(-25D, 5D, 2D, 5D));
					}
					((CategoryPlot)jfreechart.getPlot()).addRangeMarker(marker2, Layer.BACKGROUND);
				}
				
				if (chart.getProperty(PROPERTY_MOUSE_MARKER_SELECTED, Boolean.class, Boolean.FALSE)) {
					renderer.setBaseToolTipGenerator(
							new StandardCategoryToolTipGenerator("{2}", SpringLocaleDelegate.getInstance().getNumberFormat()));
				}
				if (chart.getProperty(PROPERTY_SHOW_MARKER_SELECTED, Boolean.class, Boolean.FALSE)) {
					renderer.setBaseItemLabelGenerator(
	                        new StandardCategoryItemLabelGenerator("{2}", SpringLocaleDelegate.getInstance().getNumberFormat()));
					renderer.setBaseItemLabelsVisible(true);
				}
				
				return jfreechart;
			}
			@Override
			public List<Pair<String, String>> getChartProperties(Chart c, JFreeChart chart) {
				List<Pair<String, String>> result = new ArrayList<Pair<String,String>>();
				
				result.addAll(getCommonChartProperties(chart));

				if (!(chart.getPlot() instanceof CategoryPlot)) {
					return result;
				}
				
				// set properties for category plot
				result.add(new Pair<String, String>(Chart.PROPERTY_PLOT_ORIENTATION, "" + ((CategoryPlot)chart.getPlot()).getOrientation().toString()));
				// set properties for domain axis
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_LABEL, "" + ((CategoryPlot)chart.getPlot()).getDomainAxis().getLabel()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_LABEL_FONT, "" + fontToString(((CategoryPlot)chart.getPlot()).getDomainAxis().getLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_LABEL_COLOR, "" + ((Color)((CategoryPlot)chart.getPlot()).getDomainAxis().getLabelPaint()).getRGB()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_TICK_LABELS_FONT, "" + fontToString(((CategoryPlot)chart.getPlot()).getDomainAxis().getTickLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_TICK_LABELS_VISIBLE, "" + ((CategoryPlot)chart.getPlot()).getDomainAxis().isTickLabelsVisible()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_TICK_MARKS_VISIBLE, "" + ((CategoryPlot)chart.getPlot()).getDomainAxis().isTickMarksVisible()));
				// set properties for range axis
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL, "" + ((CategoryPlot)chart.getPlot()).getRangeAxis().getLabel()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL_FONT, "" + fontToString(((CategoryPlot)chart.getPlot()).getRangeAxis().getLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL_COLOR, "" + ((Color)((CategoryPlot)chart.getPlot()).getRangeAxis().getLabelPaint()).getRGB()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_LABELS_FONT, "" + fontToString(((CategoryPlot)chart.getPlot()).getRangeAxis().getTickLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_LABELS_VISIBLE, "" + ((CategoryPlot)chart.getPlot()).getRangeAxis().isTickLabelsVisible()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_MARKS_VISIBLE, "" + ((CategoryPlot)chart.getPlot()).getRangeAxis().isTickMarksVisible()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_AUTORANGE, "" + ((CategoryPlot)chart.getPlot()).getRangeAxis().isAutoRange()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LOWERBOUND, "" + ((CategoryPlot)chart.getPlot()).getRangeAxis().getLowerBound()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_UPPERBOUND, "" + ((CategoryPlot)chart.getPlot()).getRangeAxis().getUpperBound()));
				
				return result;
			}			
			@Override
			public ChartColumn[] getValueColumnDesc(boolean withScaleColumn) {
				return withScaleColumn ? new ChartColumn[] {
						new ChartColumn(Chart.PROPERTY_VALUE_COLUMN, Chart.PROPERTY_VALUE_COLUMN, Number.class),
						new ChartColumn(Chart.PROPERTY_VALUE_SCALE_COLUMN, Chart.PROPERTY_VALUE_SCALE_COLUMN, Number.class)
				} : new ChartColumn[] {
						new ChartColumn(Chart.PROPERTY_VALUE_COLUMN, Chart.PROPERTY_VALUE_COLUMN, Number.class)
				};
			}
			@Override
			public ChartColumn[] getDomainColumnDesc() {
				return new ChartColumn[] {
						new ChartColumn(Chart.PROPERTY_DOMAIN_COLUMN, Chart.PROPERTY_DOMAIN_COLUMN, Comparable.class),
						new ChartColumn(Chart.PROPERTY_RANGE_COLUMN, Chart.PROPERTY_RANGE_COLUMN, Comparable.class),
						new ChartColumn(Chart.PROPERTY_RANGE_TYPE, Chart.PROPERTY_RANGE_TYPE, ChartRangeType.class),
						new ChartColumn(Chart.PROPERTY_ITEMWIDTH_COLUMN, Chart.PROPERTY_ITEMWIDTH_COLUMN, Number.class),
						new ChartColumn(Chart.PROPERTY_ITEMCOLOR_COLUMN, Chart.PROPERTY_ITEMCOLOR_COLUMN, String.class),
						new ChartColumn(Chart.PROPERTY_BARCHART_STANDARDRANGE_COLUMN, Chart.PROPERTY_BARCHART_STANDARDRANGE_COLUMN, String.class),
						new ChartColumn(Chart.PROPERTY_DOMAIN_MARKER_COLUMN, Chart.PROPERTY_DOMAIN_MARKER_COLUMN, Comparable.class),
						new ChartColumn(Chart.PROPERTY_RANGE_MARKER_COLUMN, Chart.PROPERTY_RANGE_MARKER_COLUMN, Number.class)
				};
			}		
			@Override
			public JTabbedPanel[] getCustomPlotEditorPanels(Chart chart, String prefix) {
				CustomChartEditor editor = new DefaultPlotMarkerEditor(chart, prefix);
				return new JTabbedPanel[] {
						new JTabbedPanel(editor.getName(), editor)
				};
			}
		},
		XYBARCHART {
			@Override
			protected Dataset[] createDataset(Chart chart) {
				return createDataset(chart, "");
			}
			@Override
			protected Dataset[] createDataset(Chart chart, String prefix) {
				XYSeriesCollection xyseriesdataset = new XYSeriesCollection();
				
				double dItemWidth = -1d;
				
			    try {
					JTable tbl = chart.getSubForm().getJTable();
					
					TableColumn cValue = tbl.getColumn(chart.getProperty(prefix + PROPERTY_VALUE_COLUMN, UID.class));
					
					UID pDomainAxis = chart.getProperty(prefix + PROPERTY_DOMAIN_COLUMN, UID.class);
					TableColumn cDomainAxis = pDomainAxis == null ? null : tbl.getColumn(pDomainAxis);
					
					UID pItemWidth = chart.getProperty(prefix + PROPERTY_ITEMWIDTH_COLUMN, UID.class);
					TableColumn cItemWidth = pItemWidth == null ? null : tbl.getColumn(pItemWidth);
					UID pItemColor = chart.getProperty(prefix + PROPERTY_ITEMCOLOR_COLUMN, UID.class);
					TableColumn cItemColor = pItemColor == null ? null : tbl.getColumn(pItemColor);

					UID pDomainMarker = chart.getProperty(prefix + PROPERTY_DOMAIN_MARKER_COLUMN, UID.class);
					TableColumn cDomainMarker = pDomainMarker == null ? null : tbl.getColumn(pDomainMarker);
					UID pRangeMarker = chart.getProperty(prefix + PROPERTY_RANGE_MARKER_COLUMN, UID.class);
					TableColumn cRangeMarker = pRangeMarker == null ? null : tbl.getColumn(pRangeMarker);
					
					if (tbl.getModel() instanceof SearchConditionTableModelImpl) {
						return new Dataset[]{xyseriesdataset};
					}
					if (tbl.getColumnCount() > 0) {
						if (tbl.getRowCount() <= 0) {
							; // nothing
						} else {
							final XYSeries xySeries = new XYSeries("", false, true);
							
							for (int j = 0; j < tbl.getRowCount(); j++) {
								CollectableField vValue = (CollectableField)tbl.getValueAt(j, cValue.getModelIndex());
								CollectableField vXAxis = (CollectableField)tbl.getValueAt(j, cDomainAxis.getModelIndex());
								
								xySeries.addOrUpdate(vXAxis == null || vXAxis.getValue() == null ? 0 : (Number)vXAxis.getValue(), vValue == null || vValue.getValue() == null ? 0 : (Number)vValue.getValue());
								
								if (cItemWidth != null) {
									CollectableField vItemWidth = (CollectableField)tbl.getValueAt(j, cItemWidth.getModelIndex());
									if (vItemWidth != null && vItemWidth.getValue() != null) {
										dItemWidth = Math.max(dItemWidth, ((Number)vItemWidth.getValue()).doubleValue());
									}
								}
								CollectableField vItemColor= cItemColor == null ? null : (CollectableField)tbl.getValueAt(j, cItemColor.getModelIndex());
								chart.setProperty(prefix + PROPERTY_ITEMCOLOR_COLUMN_VALUE + ":" + 0, vItemColor == null || vItemColor.getValue() == null ? "" : (String)vItemColor.getValue());

								CollectableField vDomainMarker= cDomainMarker == null ? null : (CollectableField)tbl.getValueAt(j, cDomainMarker.getModelIndex());
								chart.setProperty(prefix + PROPERTY_DOMAIN_MARKER_COLUMN_VALUE, vDomainMarker == null || vDomainMarker.getValue() == null ? "" : (String)vDomainMarker.getValue()); 
								CollectableField vRangeMarker= cRangeMarker == null ? null : (CollectableField)tbl.getValueAt(j, cRangeMarker.getModelIndex());
								chart.setProperty(prefix + PROPERTY_RANGE_MARKER_COLUMN_VALUE, vRangeMarker == null || vRangeMarker.getValue() == null ? "" : (Number)vRangeMarker.getValue()); 
							}
							
							xyseriesdataset.addSeries(xySeries);
						}
					}
				} catch (Exception e) {
					LOG.warn(e.getMessage(), e);
				}
			    
		        xyseriesdataset.setAutoWidth(false);
		        if (dItemWidth < 0) {
		        	return new Dataset[]{xyseriesdataset};
		        }
	        	return new Dataset[]{new XYBarDataset(xyseriesdataset, dItemWidth)};
	        }
			@Override
			public JFreeChart createChart(Chart chart) {
				IntervalXYDataset dataset = (IntervalXYDataset)createDataset(chart)[0];
				
				JFreeChart jfreechart = ChartFactory.createXYBarChart("", "", false, "", dataset, PlotOrientation.VERTICAL, true, false, false);
				jfreechart.getPlot().setDrawingSupplier(new DefaultDrawingSupplier());
				ChartUtilities.applyCurrentTheme(jfreechart);
			    
				setCommonChartProperties(chart, jfreechart);

				if (!(jfreechart.getPlot() instanceof XYPlot)) {
					return jfreechart;
				}
				
				XYBarRenderer renderer = (XYBarRenderer)((XYPlot)jfreechart.getPlot()).getRenderer();
				
				((XYBarRenderer)renderer).setShadowVisible(false);
				((XYBarRenderer)renderer).setShadowXOffset(0d);
				((XYBarRenderer)renderer).setShadowYOffset(0d);
				
				final String legendLabel = chart.getProperty(PROPERTY_LEGEND_LABEL, String.class, "");
				renderer.setLegendItemLabelGenerator(
			    	      new StandardXYSeriesLabelGenerator() {
			    	    	  @Override
			    	    	  public String generateLabel(XYDataset dataset, int series) {
			    	    		  return legendLabel;
			    	    	  }
			    	        }
			    	    );
				
				// set properties for xy plot
				((XYPlot)jfreechart.getPlot()).setOrientation(
						chart.getProperty(PROPERTY_PLOT_ORIENTATION, String.class, PlotOrientation.HORIZONTAL.toString())
							.equals(PlotOrientation.VERTICAL.toString()) ? PlotOrientation.VERTICAL : PlotOrientation.HORIZONTAL);
				// set properties for domain axis
				((XYPlot)jfreechart.getPlot()).getDomainAxis().setLabel(chart.getProperty(PROPERTY_DOMAIN_LABEL, String.class));
				if (chart.getProperty(PROPERTY_DOMAIN_LABEL_FONT, Font.class) != null) {
					((XYPlot)jfreechart.getPlot()).getDomainAxis().setLabelFont(chart.getProperty(PROPERTY_DOMAIN_LABEL_FONT, Font.class));
				}
				((XYPlot)jfreechart.getPlot()).getDomainAxis().setLabelPaint(chart.getProperty(PROPERTY_DOMAIN_LABEL_COLOR, Color.class, Color.BLACK));
				if (chart.getProperty(PROPERTY_DOMAIN_TICK_LABELS_FONT, Font.class) != null) {
					((XYPlot)jfreechart.getPlot()).getDomainAxis().setTickLabelFont(chart.getProperty(PROPERTY_DOMAIN_TICK_LABELS_FONT, Font.class));
				}
				((XYPlot)jfreechart.getPlot()).getDomainAxis().setTickLabelsVisible(chart.getProperty(PROPERTY_DOMAIN_TICK_LABELS_VISIBLE, Boolean.class, Boolean.TRUE));
				((XYPlot)jfreechart.getPlot()).getDomainAxis().setTickMarksVisible(chart.getProperty(PROPERTY_DOMAIN_TICK_MARKS_VISIBLE, Boolean.class, Boolean.TRUE));
				((XYPlot)jfreechart.getPlot()).getDomainAxis().setAutoRange(chart.getProperty(PROPERTY_DOMAIN_AUTORANGE, Boolean.class, Boolean.TRUE));
				if (!((XYPlot)jfreechart.getPlot()).getDomainAxis().isAutoRange()) {
					((XYPlot)jfreechart.getPlot()).getDomainAxis().setLowerBound(chart.getProperty(PROPERTY_DOMAIN_LOWERBOUND, Double.class, 0D));
					((XYPlot)jfreechart.getPlot()).getDomainAxis().setUpperBound(chart.getProperty(PROPERTY_DOMAIN_UPPERBOUND, Double.class, 0D));
				}
				// set properties for range axis
				((XYPlot)jfreechart.getPlot()).getRangeAxis().setLabel(chart.getProperty(PROPERTY_RANGE_LABEL, String.class));
				if (chart.getProperty(PROPERTY_RANGE_LABEL_FONT, Font.class) != null) {
					((XYPlot)jfreechart.getPlot()).getRangeAxis().setLabelFont(chart.getProperty(PROPERTY_RANGE_LABEL_FONT, Font.class));
				}
				((XYPlot)jfreechart.getPlot()).getRangeAxis().setLabelPaint(chart.getProperty(PROPERTY_RANGE_LABEL_COLOR, Color.class, Color.BLACK));
				if (chart.getProperty(PROPERTY_RANGE_TICK_LABELS_FONT, Font.class) != null) {
					((XYPlot)jfreechart.getPlot()).getRangeAxis().setTickLabelFont(chart.getProperty(PROPERTY_RANGE_TICK_LABELS_FONT, Font.class));
				}
				((XYPlot)jfreechart.getPlot()).getRangeAxis().setTickLabelsVisible(chart.getProperty(PROPERTY_RANGE_TICK_LABELS_VISIBLE, Boolean.class, Boolean.TRUE));
				((XYPlot)jfreechart.getPlot()).getRangeAxis().setTickMarksVisible(chart.getProperty(PROPERTY_RANGE_TICK_MARKS_VISIBLE, Boolean.class, Boolean.TRUE));
				((XYPlot)jfreechart.getPlot()).getRangeAxis().setAutoRange(chart.getProperty(PROPERTY_RANGE_AUTORANGE, Boolean.class, Boolean.TRUE));
				if (!((XYPlot)jfreechart.getPlot()).getRangeAxis().isAutoRange()) {
					((XYPlot)jfreechart.getPlot()).getRangeAxis().setLowerBound(chart.getProperty(PROPERTY_RANGE_LOWERBOUND, Double.class, 0D));
					((XYPlot)jfreechart.getPlot()).getRangeAxis().setUpperBound(chart.getProperty(PROPERTY_RANGE_UPPERBOUND, Double.class, 0D));
				}

				// set colors. width can be set on dataset directly here.
				Color cItemColor = chart.getProperty(PROPERTY_ITEMCOLOR_COLUMN_VALUE + ":" + 0, Color.class);
				if (cItemColor != null) {
					((AbstractRenderer)((XYPlot)jfreechart.getPlot()).getRenderer(0)).setSeriesPaint(0, cItemColor);
				}
				
				// set markers.
				Object cDomainMarker = chart.getProperty(PROPERTY_DOMAIN_MARKER_COLUMN_VALUE, Number.class);
				if (cDomainMarker instanceof Number) {
					ValueMarker marker1 = new ValueMarker(((Number)cDomainMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker1.setLabel(chart.getProperty(PROPERTY_DOMAIN_MARKER_LABEL, String.class));
					marker1.setLabelFont(new Font("Dialog", 0, 11));
					marker1.setLabelPaint(Color.DARK_GRAY);
					if (((XYPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.VERTICAL) {
						marker1.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
					} else {
						marker1.setLabelAnchor(RectangleAnchor.RIGHT);
						marker1.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
					}
					marker1.setLabelOffset(new RectangleInsets(2D, 5D, 2D, 5D));
					((XYPlot)jfreechart.getPlot()).addDomainMarker(marker1, Layer.BACKGROUND);
					ValueMarker marker2 = new ValueMarker(((Number)cDomainMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker2.setLabel("" + CollectableFieldFormat.getInstance(Double.class).format("#,##0.00", marker2.getValue()));
					marker2.setLabelFont(new Font("Dialog", 0, 11));
					marker2.setLabelPaint(Color.DARK_GRAY);
					if (((XYPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.VERTICAL) {
						marker2.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(15D, 5D, 2D, 5D));
					} else {
						marker2.setLabelAnchor(RectangleAnchor.RIGHT);
						marker2.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(-25D, 5D, 2D, 5D));
					}
					((XYPlot)jfreechart.getPlot()).addDomainMarker(marker2, Layer.BACKGROUND);
				}
				Object cRangeMarker = chart.getProperty(PROPERTY_RANGE_MARKER_COLUMN_VALUE, Number.class);
				if (cRangeMarker instanceof Number) {
					ValueMarker marker1 = new ValueMarker(((Number)cRangeMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker1.setLabel(chart.getProperty(PROPERTY_RANGE_MARKER_LABEL, String.class));
					marker1.setLabelFont(new Font("Dialog", 0, 11));
					marker1.setLabelPaint(Color.DARK_GRAY);
					if (((XYPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.HORIZONTAL) {
						marker1.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
					} else {
						marker1.setLabelAnchor(RectangleAnchor.RIGHT);
						marker1.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
					}
					marker1.setLabelOffset(new RectangleInsets(2D, 5D, 2D, 5D));
					((XYPlot)jfreechart.getPlot()).addRangeMarker(marker1, Layer.BACKGROUND);
					ValueMarker marker2 = new ValueMarker(((Number)cRangeMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker2.setLabel("" + CollectableFieldFormat.getInstance(Double.class).format("#,##0.00", marker2.getValue()));
					marker2.setLabelFont(new Font("Dialog", 0, 11));
					marker2.setLabelPaint(Color.DARK_GRAY);	
					if (((XYPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.HORIZONTAL) {
						marker2.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(15D, 5D, 2D, 5D));
					} else {
						marker2.setLabelAnchor(RectangleAnchor.RIGHT);
						marker2.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(-25D, 5D, 2D, 5D));
					}
					((XYPlot)jfreechart.getPlot()).addRangeMarker(marker2, Layer.BACKGROUND);
				}
				
				if (chart.getProperty(PROPERTY_MOUSE_MARKER_SELECTED, Boolean.class, Boolean.FALSE)) {
					renderer.setBaseToolTipGenerator(
							new StandardXYToolTipGenerator("{2}", SpringLocaleDelegate.getInstance().getDateFormat(), SpringLocaleDelegate.getInstance().getNumberFormat()));
				}
				if (chart.getProperty(PROPERTY_SHOW_MARKER_SELECTED, Boolean.class, Boolean.FALSE)) {
					renderer.setBaseItemLabelGenerator(
							new StandardXYItemLabelGenerator("{2}", SpringLocaleDelegate.getInstance().getDateFormat(), SpringLocaleDelegate.getInstance().getNumberFormat()));
					renderer.setBaseItemLabelsVisible(true);
				}
				
				return jfreechart;
			}
			@Override
			public List<Pair<String, String>> getChartProperties(Chart c, JFreeChart chart) {
				List<Pair<String, String>> result = new ArrayList<Pair<String,String>>();
				
				result.addAll(getCommonChartProperties(chart));

				if (!(chart.getPlot() instanceof XYPlot)) {
					return result;
				}
				
				// set properties for xy plot
				result.add(new Pair<String, String>(Chart.PROPERTY_PLOT_ORIENTATION, "" + ((XYPlot)chart.getPlot()).getOrientation().toString()));
				// set properties for domain axis
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_LABEL, "" + ((XYPlot)chart.getPlot()).getDomainAxis().getLabel()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_LABEL_FONT, "" + fontToString(((XYPlot)chart.getPlot()).getDomainAxis().getLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_LABEL_COLOR, "" + ((Color)((XYPlot)chart.getPlot()).getDomainAxis().getLabelPaint()).getRGB()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_TICK_LABELS_FONT, "" + fontToString(((XYPlot)chart.getPlot()).getDomainAxis().getTickLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_TICK_LABELS_VISIBLE, "" + ((XYPlot)chart.getPlot()).getDomainAxis().isTickLabelsVisible()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_TICK_MARKS_VISIBLE, "" + ((XYPlot)chart.getPlot()).getDomainAxis().isTickMarksVisible()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_AUTORANGE, "" + ((XYPlot)chart.getPlot()).getDomainAxis().isAutoRange()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_LOWERBOUND, "" + ((XYPlot)chart.getPlot()).getDomainAxis().getLowerBound()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_UPPERBOUND, "" + ((XYPlot)chart.getPlot()).getDomainAxis().getUpperBound()));
				// set properties for range axis
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL, "" + ((XYPlot)chart.getPlot()).getRangeAxis().getLabel()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL_FONT, "" + fontToString(((XYPlot)chart.getPlot()).getRangeAxis().getLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL_COLOR, "" + ((Color)((XYPlot)chart.getPlot()).getRangeAxis().getLabelPaint()).getRGB()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_LABELS_FONT, "" + fontToString(((XYPlot)chart.getPlot()).getRangeAxis().getTickLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_LABELS_VISIBLE, "" + ((XYPlot)chart.getPlot()).getRangeAxis().isTickLabelsVisible()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_MARKS_VISIBLE, "" + ((XYPlot)chart.getPlot()).getRangeAxis().isTickMarksVisible()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_AUTORANGE, "" + ((XYPlot)chart.getPlot()).getRangeAxis().isAutoRange()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LOWERBOUND, "" + ((XYPlot)chart.getPlot()).getRangeAxis().getLowerBound()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_UPPERBOUND, "" + ((XYPlot)chart.getPlot()).getRangeAxis().getUpperBound()));
				
				return result;
			}			
			@Override
			public ChartColumn[] getValueColumnDesc(boolean withScaleColumn) {
				return new ChartColumn[] {
						new ChartColumn(Chart.PROPERTY_VALUE_COLUMN, Chart.PROPERTY_VALUE_COLUMN, Number.class)
				};
			}
			@Override
			public ChartColumn[] getDomainColumnDesc() {
				return new ChartColumn[] {
						new ChartColumn(Chart.PROPERTY_DOMAIN_COLUMN, Chart.PROPERTY_DOMAIN_COLUMN, Comparable.class),
						new ChartColumn(Chart.PROPERTY_ITEMWIDTH_COLUMN, Chart.PROPERTY_ITEMWIDTH_COLUMN, Number.class),
						new ChartColumn(Chart.PROPERTY_ITEMCOLOR_COLUMN, Chart.PROPERTY_ITEMCOLOR_COLUMN, String.class),
						new ChartColumn(Chart.PROPERTY_DOMAIN_MARKER_COLUMN, Chart.PROPERTY_DOMAIN_MARKER_COLUMN, Comparable.class),
						new ChartColumn(Chart.PROPERTY_RANGE_MARKER_COLUMN, Chart.PROPERTY_RANGE_MARKER_COLUMN, Number.class)
				};
			}		
			@Override
			public JTabbedPanel[] getCustomPlotEditorPanels(Chart chart, String prefix) {
				CustomChartEditor editor = new DefaultPlotMarkerEditor(chart, prefix);
				CustomChartEditor editorLegend = new DefaultLegendEditor(chart, prefix);
				return new JTabbedPanel[] {
						new JTabbedPanel(editor.getName(), editor),
						new JTabbedPanel(editorLegend.getName(), editorLegend)
				};
			}
		},
		PIECHART {
			@Override
			protected Dataset[] createDataset(Chart chart) {
				DefaultPieDataset defaultpiedataset = new DefaultPieDataset();
				
				try {
					JTable tbl = chart.getSubForm().getJTable();
					
					TableColumn cValue = tbl.getColumn(chart.getProperty(PROPERTY_VALUE_COLUMN, UID.class));
					
					TableColumn cDomainAxis = tbl.getColumn(chart.getProperty(PROPERTY_DOMAIN_COLUMN, UID.class));
					
					if (tbl.getModel() instanceof SearchConditionTableModelImpl) {
						defaultpiedataset.setValue("", 0);
						return new Dataset[]{defaultpiedataset};
					}
					if (tbl.getColumnCount() > 0) {
						if (tbl.getRowCount() <= 0) {
							defaultpiedataset.setValue("", 0);
						} else {
							for (int j = 0; j < tbl.getRowCount(); j++) {
								CollectableField vValue = (CollectableField)tbl.getValueAt(j, cValue.getModelIndex());
								CollectableField vXAxis = (CollectableField)tbl.getValueAt(j, cDomainAxis.getModelIndex());
								
								defaultpiedataset.setValue(vXAxis == null || vXAxis.getValue() == null ? "" : (Comparable)vXAxis.getValue(), vValue == null || vValue.getValue() == null ? 0 : (Number)vValue.getValue());
							}
						}
					}
				} catch (Exception e) {
					LOG.warn(e.getMessage(), e);
				}
				
				return new Dataset[]{defaultpiedataset};
	        }
			@Override
			public JFreeChart createChart(Chart chart) {
				PieDataset dataset = (PieDataset)createDataset(chart)[0];
				
				JFreeChart jfreechart = ChartFactory.createPieChart("", dataset, true, false, false);
				jfreechart.getPlot().setDrawingSupplier(new DefaultDrawingSupplier());
				ChartUtilities.applyCurrentTheme(jfreechart);
			    
				setCommonChartProperties(chart, jfreechart);

				if (!(jfreechart.getPlot() instanceof PiePlot)) {
					return jfreechart;
				}
				
				// set properties for pie plot
		        
				((PiePlot)jfreechart.getPlot()).setLabelGenerator(new StandardPieSectionLabelGenerator("{2}"));
				((PiePlot)jfreechart.getPlot()).setSimpleLabels(true);
				((PiePlot)jfreechart.getPlot()).setLabelBackgroundPaint(new Color(0, 0, 0, 0));
				((PiePlot)jfreechart.getPlot()).setLabelOutlinePaint(new Color(0, 0, 0, 0));
				((PiePlot)jfreechart.getPlot()).setLabelShadowPaint(new Color(0, 0, 0, 0));
				
				return jfreechart;
			}
			@Override
			public List<Pair<String, String>> getChartProperties(Chart c, JFreeChart chart) {
				List<Pair<String, String>> result = new ArrayList<Pair<String,String>>();
				
				result.addAll(getCommonChartProperties(chart));

				if (!(chart.getPlot() instanceof PiePlot)) {
					return result;
				}
				
				// set properties for pie plot
				
				// nothing to do.
				
				return result;
			}			
			@Override
			public ChartColumn[] getValueColumnDesc(boolean withScaleColumn) {
				return new ChartColumn[] {
						new ChartColumn(Chart.PROPERTY_VALUE_COLUMN, Chart.PROPERTY_VALUE_COLUMN, Number.class)
				};
			}
			@Override
			public ChartColumn[] getDomainColumnDesc() {
				return new ChartColumn[] {
						new ChartColumn(Chart.PROPERTY_DOMAIN_COLUMN, Chart.PROPERTY_DOMAIN_COLUMN, Comparable.class)
				};
			}
		},
		LINECHART {
			@Override
			protected Dataset[] createDataset(Chart chart) {
				return createDataset(chart, "");
			}
			@Override
			protected Dataset[] createDataset(Chart chart, String prefix) {
				DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
				
				try {
					JTable tbl = chart.getSubForm().getJTable();
					
					TableColumn cValue = tbl.getColumn(chart.getProperty(prefix + PROPERTY_VALUE_COLUMN, UID.class));
					
					UID pDomainAxis = chart.getProperty(prefix + PROPERTY_DOMAIN_COLUMN, UID.class);
					TableColumn cDomainAxis = pDomainAxis == null ? null : tbl.getColumn(pDomainAxis);
					UID pRangeAxis = chart.getProperty(prefix + PROPERTY_RANGE_COLUMN, UID.class);
					TableColumn cRangeAxis = pRangeAxis == null ? null : tbl.getColumn(pRangeAxis);
					
					UID pItemWidth = chart.getProperty(prefix + PROPERTY_ITEMWIDTH_COLUMN, UID.class);
					TableColumn cItemWidth = pItemWidth == null ? null : tbl.getColumn(pItemWidth);
					UID pItemColor = chart.getProperty(prefix + PROPERTY_ITEMCOLOR_COLUMN, UID.class);
					TableColumn cItemColor = pItemColor == null ? null : tbl.getColumn(pItemColor);

					UID pDomainMarker = chart.getProperty(prefix + PROPERTY_DOMAIN_MARKER_COLUMN, UID.class);
					TableColumn cDomainMarker = pDomainMarker == null ? null : tbl.getColumn(pDomainMarker);
					UID pRangeMarker = chart.getProperty(prefix + PROPERTY_RANGE_MARKER_COLUMN, UID.class);
					TableColumn cRangeMarker = pRangeMarker == null ? null : tbl.getColumn(pRangeMarker);

					if (tbl.getModel() instanceof SearchConditionTableModelImpl) {
						defaultcategorydataset.addValue(0, "", "");
						return new Dataset[]{defaultcategorydataset};
					}

					if (tbl.getColumnCount() > 0) {
						if (tbl.getRowCount() <= 0) {
							defaultcategorydataset.addValue(0, "", "");
						} else {
							for (int j = 0; j < tbl.getRowCount(); j++) {
								CollectableField vValue = (CollectableField)tbl.getValueAt(j, cValue.getModelIndex());
								
								CollectableField vDomainAxis = cDomainAxis == null ? null : (CollectableField)tbl.getValueAt(j, cDomainAxis.getModelIndex());
								CollectableField vRangeAxis = (CollectableField)tbl.getValueAt(j, cRangeAxis.getModelIndex());
								
								defaultcategorydataset.addValue(vValue == null || vValue.getValue() == null ? 0 : (Number)vValue.getValue(),
									vDomainAxis == null || vDomainAxis.getValue() == null ? "" : (Comparable)vDomainAxis.getValue(), vRangeAxis == null || vRangeAxis.getValue() == null ? "" : (Comparable)vRangeAxis.getValue());
								
								CollectableField vItemWidth= cItemWidth == null ? null : (CollectableField)tbl.getValueAt(j, cItemWidth.getModelIndex());
								chart.setProperty(prefix + PROPERTY_ITEMWIDTH_COLUMN_VALUE + ":" + defaultcategorydataset.getRowKeys().indexOf(
										vDomainAxis == null || vDomainAxis.getValue() == null ? "" : (Comparable)vDomainAxis.getValue()), vItemWidth == null || vItemWidth.getValue() == null ? "" : (Number)vItemWidth.getValue());
								CollectableField vItemColor= cItemColor == null ? null : (CollectableField)tbl.getValueAt(j, cItemColor.getModelIndex());
								chart.setProperty(prefix + PROPERTY_ITEMCOLOR_COLUMN_VALUE + ":" + defaultcategorydataset.getRowKeys().indexOf(
										vDomainAxis == null || vDomainAxis.getValue() == null ? "" : (Comparable)vDomainAxis.getValue()), vItemColor == null || vItemColor.getValue() == null ? "" : (String)vItemColor.getValue());

								CollectableField vDomainMarker= cDomainMarker == null ? null : (CollectableField)tbl.getValueAt(j, cDomainMarker.getModelIndex());
								chart.setProperty(prefix + PROPERTY_DOMAIN_MARKER_COLUMN_VALUE, vDomainMarker == null || vDomainMarker.getValue() == null ? "" : (String)vDomainMarker.getValue()); 
								CollectableField vRangeMarker= cRangeMarker == null ? null : (CollectableField)tbl.getValueAt(j, cRangeMarker.getModelIndex());
								chart.setProperty(prefix + PROPERTY_RANGE_MARKER_COLUMN_VALUE, vRangeMarker == null || vRangeMarker.getValue() == null ? "" : (Number)vRangeMarker.getValue());
							}
						}
					}
				} catch (Exception e) {
					LOG.warn(e.getMessage(), e);
				}
				
				return new Dataset[]{defaultcategorydataset};
	        }
			@Override
			public JFreeChart createChart(Chart chart) {
				CategoryDataset dataset = (CategoryDataset)createDataset(chart)[0];
				
				JFreeChart jfreechart = ChartFactory.createLineChart("", "", "", dataset, PlotOrientation.VERTICAL, true, false, false);
				jfreechart.getPlot().setDrawingSupplier(new DefaultDrawingSupplier());
				ChartUtilities.applyCurrentTheme(jfreechart);
			    
				setCommonChartProperties(chart, jfreechart);
				if (!(jfreechart.getPlot() instanceof CategoryPlot)) {
					return jfreechart;
				}
				
				LineAndShapeRenderer renderer = new LineAndShapeRenderer(true, false) {
					@Override
					public Stroke getItemStroke(int row, int column) {
						if (column == 0)
							return new BasicStroke();
						return super.getItemStroke(row, column);
					}
				};
			
				((CategoryPlot)jfreechart.getPlot()).setRenderer(0, renderer);

				// set properties for category plot
				((CategoryPlot)jfreechart.getPlot()).setOrientation(
						chart.getProperty(PROPERTY_PLOT_ORIENTATION, String.class, PlotOrientation.HORIZONTAL.toString())
							.equals(PlotOrientation.VERTICAL.toString()) ? PlotOrientation.VERTICAL : PlotOrientation.HORIZONTAL);
				// set properties for domain axis
				((CategoryPlot)jfreechart.getPlot()).getDomainAxis().setLabel(chart.getProperty(PROPERTY_DOMAIN_LABEL, String.class));
				if (chart.getProperty(PROPERTY_DOMAIN_LABEL_FONT, Font.class) != null) {
					((CategoryPlot)jfreechart.getPlot()).getDomainAxis().setLabelFont(chart.getProperty(PROPERTY_DOMAIN_LABEL_FONT, Font.class));
				}
				((CategoryPlot)jfreechart.getPlot()).getDomainAxis().setLabelPaint(chart.getProperty(PROPERTY_DOMAIN_LABEL_COLOR, Color.class, Color.BLACK));
				if (chart.getProperty(PROPERTY_DOMAIN_TICK_LABELS_FONT, Font.class) != null) {
					((CategoryPlot)jfreechart.getPlot()).getDomainAxis().setTickLabelFont(chart.getProperty(PROPERTY_DOMAIN_TICK_LABELS_FONT, Font.class));
				}
				((CategoryPlot)jfreechart.getPlot()).getDomainAxis().setTickLabelsVisible(chart.getProperty(PROPERTY_DOMAIN_TICK_LABELS_VISIBLE, Boolean.class, Boolean.TRUE));
				((CategoryPlot)jfreechart.getPlot()).getDomainAxis().setTickMarksVisible(chart.getProperty(PROPERTY_DOMAIN_TICK_MARKS_VISIBLE, Boolean.class, Boolean.TRUE));
				// set properties for range axis
				((CategoryPlot)jfreechart.getPlot()).getRangeAxis().setLabel(chart.getProperty(PROPERTY_RANGE_LABEL, String.class));
				if (chart.getProperty(PROPERTY_RANGE_LABEL_FONT, Font.class) != null) {
					((CategoryPlot)jfreechart.getPlot()).getRangeAxis().setLabelFont(chart.getProperty(PROPERTY_RANGE_LABEL_FONT, Font.class));
				}
				((CategoryPlot)jfreechart.getPlot()).getRangeAxis().setLabelPaint(chart.getProperty(PROPERTY_RANGE_LABEL_COLOR, Color.class, Color.BLACK));
				if (chart.getProperty(PROPERTY_RANGE_TICK_LABELS_FONT, Font.class) != null) {
					((CategoryPlot)jfreechart.getPlot()).getRangeAxis().setTickLabelFont(chart.getProperty(PROPERTY_RANGE_TICK_LABELS_FONT, Font.class));
				}
				((CategoryPlot)jfreechart.getPlot()).getRangeAxis().setTickLabelsVisible(chart.getProperty(PROPERTY_RANGE_TICK_LABELS_VISIBLE, Boolean.class, Boolean.TRUE));
				((CategoryPlot)jfreechart.getPlot()).getRangeAxis().setTickMarksVisible(chart.getProperty(PROPERTY_RANGE_TICK_MARKS_VISIBLE, Boolean.class, Boolean.TRUE));
				((CategoryPlot)jfreechart.getPlot()).getRangeAxis().setAutoRange(chart.getProperty(PROPERTY_RANGE_AUTORANGE, Boolean.class, Boolean.TRUE));
				if (!((CategoryPlot)jfreechart.getPlot()).getRangeAxis().isAutoRange()) {
					((CategoryPlot)jfreechart.getPlot()).getRangeAxis().setLowerBound(chart.getProperty(PROPERTY_RANGE_LOWERBOUND, Double.class, 0D));
					((CategoryPlot)jfreechart.getPlot()).getRangeAxis().setUpperBound(chart.getProperty(PROPERTY_RANGE_UPPERBOUND, Double.class, 0D));
				}
				
				// set width and colors
				for (int j = 0; j < ((CategoryPlot)jfreechart.getPlot()).getDataset(0).getRowKeys().size(); j++) {
					Object cItemWidth = chart.getProperty(PROPERTY_ITEMWIDTH_COLUMN_VALUE + ":" + j, Number.class);
					if (cItemWidth instanceof Number) {
						if (((Number)cItemWidth).floatValue() > 0) {
							((AbstractRenderer)((CategoryPlot)jfreechart.getPlot()).getRenderer(0)).setSeriesStroke(j,
									new BasicStroke(((Number)cItemWidth).floatValue()));
						}
					}
					Color cItemColor = chart.getProperty(PROPERTY_ITEMCOLOR_COLUMN_VALUE + ":" + j, Color.class);
					if (cItemColor != null) {
						((AbstractRenderer)((CategoryPlot)jfreechart.getPlot()).getRenderer(0)).setSeriesPaint(j, cItemColor);
					}
				}
				
				// set markers.
				Comparable cDomainMarker = chart.getProperty(PROPERTY_DOMAIN_MARKER_COLUMN_VALUE, String.class);
				if (cDomainMarker instanceof Number) {
					CategoryMarker marker1 = new CategoryMarker(cDomainMarker, Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker1.setDrawAsLine(true);
					marker1.setLabel(chart.getProperty(PROPERTY_DOMAIN_MARKER_LABEL, String.class));
					marker1.setLabelFont(new Font("Dialog", 0, 11));
					marker1.setLabelPaint(Color.DARK_GRAY);
					if (((CategoryPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.VERTICAL) {
						marker1.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
					} else {
						marker1.setLabelAnchor(RectangleAnchor.RIGHT);
						marker1.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
					}
					marker1.setLabelOffset(new RectangleInsets(2D, 5D, 2D, 5D));
					((CategoryPlot)jfreechart.getPlot()).addDomainMarker(marker1, Layer.BACKGROUND);
					CategoryMarker marker2 = new CategoryMarker(cDomainMarker, Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker2.setDrawAsLine(true);
					marker2.setLabel("" + marker2.getKey());
					marker2.setLabelFont(new Font("Dialog", 0, 11));
					marker2.setLabelPaint(Color.DARK_GRAY);
					if (((CategoryPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.VERTICAL) {
						marker2.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(15D, 5D, 2D, 5D));
					} else {
						marker2.setLabelAnchor(RectangleAnchor.RIGHT);
						marker2.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(-25D, 5D, 2D, 5D));
					}
					((CategoryPlot)jfreechart.getPlot()).addDomainMarker(marker2, Layer.BACKGROUND);
				}
				Object cRangeMarker = chart.getProperty(PROPERTY_RANGE_MARKER_COLUMN_VALUE, Number.class);
				if (cRangeMarker != null && cRangeMarker instanceof Number) {
					ValueMarker marker1 = new ValueMarker(((Number)cRangeMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker1.setLabel(chart.getProperty(PROPERTY_RANGE_MARKER_LABEL, String.class));
					marker1.setLabelFont(new Font("Dialog", 0, 11));
					marker1.setLabelPaint(Color.DARK_GRAY);
					if (((CategoryPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.HORIZONTAL) {
						marker1.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
					} else {
						marker1.setLabelAnchor(RectangleAnchor.RIGHT);
						marker1.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
					}
					marker1.setLabelOffset(new RectangleInsets(2D, 5D, 2D, 5D));
					((CategoryPlot)jfreechart.getPlot()).addRangeMarker(marker1, Layer.BACKGROUND);
					ValueMarker marker2 = new ValueMarker(((Number)cRangeMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker2.setLabel("" + CollectableFieldFormat.getInstance(Double.class).format("#,##0.00", marker2.getValue()));
					marker2.setLabelFont(new Font("Dialog", 0, 11));
					marker2.setLabelPaint(Color.DARK_GRAY);	
					if (((CategoryPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.HORIZONTAL) {
						marker2.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(15D, 5D, 2D, 5D));
					} else {
						marker2.setLabelAnchor(RectangleAnchor.RIGHT);
						marker2.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(-25D, 5D, 2D, 5D));
					}
					((CategoryPlot)jfreechart.getPlot()).addRangeMarker(marker2, Layer.BACKGROUND);
				}
				
				if (chart.getProperty(PROPERTY_MOUSE_MARKER_SELECTED, Boolean.class, Boolean.FALSE)) {
					renderer.setBaseToolTipGenerator(
							new StandardCategoryToolTipGenerator("{2}", SpringLocaleDelegate.getInstance().getNumberFormat()));
				}
				if (chart.getProperty(PROPERTY_SHOW_MARKER_SELECTED, Boolean.class, Boolean.FALSE)) {
					renderer.setBaseItemLabelGenerator(
	                        new StandardCategoryItemLabelGenerator("{2}", SpringLocaleDelegate.getInstance().getNumberFormat()));
					renderer.setBaseItemLabelsVisible(true);
				}
				
				return jfreechart;
			}
			@Override
			public List<Pair<String, String>> getChartProperties(Chart c, JFreeChart chart) {
				List<Pair<String, String>> result = new ArrayList<Pair<String,String>>();
				
				result.addAll(getCommonChartProperties(chart));

				if (!(chart.getPlot() instanceof CategoryPlot)) {
					return result;
				}

				// set properties for category plot
				result.add(new Pair<String, String>(Chart.PROPERTY_PLOT_ORIENTATION, "" + ((CategoryPlot)chart.getPlot()).getOrientation().toString()));
				// set properties for domain axis
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_LABEL, "" + ((CategoryPlot)chart.getPlot()).getDomainAxis().getLabel()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_LABEL_FONT, "" + fontToString(((CategoryPlot)chart.getPlot()).getDomainAxis().getLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_LABEL_COLOR, "" + ((Color)((CategoryPlot)chart.getPlot()).getDomainAxis().getLabelPaint()).getRGB()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_TICK_LABELS_FONT, "" + fontToString(((CategoryPlot)chart.getPlot()).getDomainAxis().getTickLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_TICK_LABELS_VISIBLE, "" + ((CategoryPlot)chart.getPlot()).getDomainAxis().isTickLabelsVisible()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_TICK_MARKS_VISIBLE, "" + ((CategoryPlot)chart.getPlot()).getDomainAxis().isTickMarksVisible()));
				// set properties for range axis
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL, "" + ((CategoryPlot)chart.getPlot()).getRangeAxis().getLabel()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL_FONT, "" + fontToString(((CategoryPlot)chart.getPlot()).getRangeAxis().getLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL_COLOR, "" + ((Color)((CategoryPlot)chart.getPlot()).getRangeAxis().getLabelPaint()).getRGB()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_LABELS_FONT, "" + fontToString(((CategoryPlot)chart.getPlot()).getRangeAxis().getTickLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_LABELS_VISIBLE, "" + ((CategoryPlot)chart.getPlot()).getRangeAxis().isTickLabelsVisible()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_MARKS_VISIBLE, "" + ((CategoryPlot)chart.getPlot()).getRangeAxis().isTickMarksVisible()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_AUTORANGE, "" + ((CategoryPlot)chart.getPlot()).getRangeAxis().isAutoRange()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LOWERBOUND, "" + ((CategoryPlot)chart.getPlot()).getRangeAxis().getLowerBound()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_UPPERBOUND, "" + ((CategoryPlot)chart.getPlot()).getRangeAxis().getUpperBound()));
				
				// nothing to do.
				
				return result;
			}			
			@Override
			public ChartColumn[] getValueColumnDesc(boolean withScaleColumn) {
				return withScaleColumn ? new ChartColumn[] {
						new ChartColumn(Chart.PROPERTY_VALUE_COLUMN, Chart.PROPERTY_VALUE_COLUMN, Number.class),
						new ChartColumn(Chart.PROPERTY_VALUE_SCALE_COLUMN, Chart.PROPERTY_VALUE_SCALE_COLUMN, Number.class)
				} : new ChartColumn[] {
						new ChartColumn(Chart.PROPERTY_VALUE_COLUMN, Chart.PROPERTY_VALUE_COLUMN, Number.class)
				};
			}
			@Override
			public ChartColumn[] getDomainColumnDesc() {
				return new ChartColumn[] {
						new ChartColumn(Chart.PROPERTY_DOMAIN_COLUMN, Chart.PROPERTY_DOMAIN_COLUMN, Comparable.class),
						new ChartColumn(Chart.PROPERTY_RANGE_COLUMN, Chart.PROPERTY_RANGE_COLUMN, Comparable.class),
						new ChartColumn(Chart.PROPERTY_RANGE_TYPE, Chart.PROPERTY_RANGE_TYPE, ChartRangeType.class),
						new ChartColumn(Chart.PROPERTY_ITEMWIDTH_COLUMN, Chart.PROPERTY_ITEMWIDTH_COLUMN, Number.class),
						new ChartColumn(Chart.PROPERTY_ITEMCOLOR_COLUMN, Chart.PROPERTY_ITEMCOLOR_COLUMN, String.class),
						new ChartColumn(Chart.PROPERTY_DOMAIN_MARKER_COLUMN, Chart.PROPERTY_DOMAIN_MARKER_COLUMN, Comparable.class),
						new ChartColumn(Chart.PROPERTY_RANGE_MARKER_COLUMN, Chart.PROPERTY_RANGE_MARKER_COLUMN, Number.class)
				};
			}		
			@Override
			public JTabbedPanel[] getCustomPlotEditorPanels(Chart chart, String prefix) {
				CustomChartEditor editor = new DefaultPlotMarkerEditor(chart, prefix);
				return new JTabbedPanel[] {
						new JTabbedPanel(editor.getName(), editor)
				};
			}
		},
		TIMESERIESCHART {
			@Override
			protected Dataset[] createDataset(Chart chart) {
				return createDataset(chart, "");
			}
			@Override
			protected Dataset[] createDataset(Chart chart, String prefix) {
				TimeSeriesCollection timeseriesdataset = new TimeSeriesCollection();
				
		       try {
					JTable tbl = chart.getSubForm().getJTable();

					TableColumn cValue = tbl.getColumn(chart.getProperty(prefix + PROPERTY_VALUE_COLUMN, UID.class));
					
					UID pDomainAxis = chart.getProperty(prefix + PROPERTY_DOMAIN_COLUMN, UID.class);
					TableColumn cDomainAxis = pDomainAxis == null ? null : tbl.getColumn(pDomainAxis);
					
					UID pItemWidth = chart.getProperty(prefix + PROPERTY_ITEMWIDTH_COLUMN, UID.class);
					TableColumn cItemWidth = pItemWidth == null ? null : tbl.getColumn(pItemWidth);
					UID pItemColor = chart.getProperty(prefix + PROPERTY_ITEMCOLOR_COLUMN, UID.class);
					TableColumn cItemColor = pItemColor == null ? null : tbl.getColumn(pItemColor);

					UID pDomainMarker = chart.getProperty(prefix + PROPERTY_DOMAIN_MARKER_COLUMN, UID.class);
					TableColumn cDomainMarker = pDomainMarker == null ? null : tbl.getColumn(pDomainMarker);
					UID pRangeMarker = chart.getProperty(prefix + PROPERTY_RANGE_MARKER_COLUMN, UID.class);
					TableColumn cRangeMarker = pRangeMarker == null ? null : tbl.getColumn(pRangeMarker);

					if (tbl.getModel() instanceof SearchConditionTableModelImpl) {
						return new Dataset[]{timeseriesdataset};
					}
					if (tbl.getColumnCount() > 0) {
						if (tbl.getRowCount() <= 0) {
							; // nothing
						} else {
							final TimeSeries timeSeries = new TimeSeries("", Second.class);
							
							for (int j = 0; j < tbl.getRowCount(); j++) {
								CollectableField vValue = (CollectableField)tbl.getValueAt(j, cValue.getModelIndex());
								CollectableField vXAxis = (CollectableField)tbl.getValueAt(j, cDomainAxis.getModelIndex());
								
								if (vXAxis != null && vXAxis.getValue() != null)
									timeSeries.addOrUpdate(new Second((Date)vXAxis.getValue()), vValue == null || vValue.getValue() == null ? 0 : (Number)vValue.getValue());
		
								CollectableField vItemWidth= cItemWidth == null ? null : (CollectableField)tbl.getValueAt(j, cItemWidth.getModelIndex());
								chart.setProperty(prefix + PROPERTY_ITEMWIDTH_COLUMN_VALUE + ":" + 0, vItemWidth == null || vItemWidth.getValue() == null ? "" : (Number)vItemWidth.getValue());
								CollectableField vItemColor= cItemColor == null ? null : (CollectableField)tbl.getValueAt(j, cItemColor.getModelIndex());
								chart.setProperty(prefix + PROPERTY_ITEMCOLOR_COLUMN_VALUE + ":" + 0, vItemColor == null || vItemColor.getValue() == null ? "" : (String)vItemColor.getValue());

								CollectableField vDomainMarker= cDomainMarker == null ? null : (CollectableField)tbl.getValueAt(j, cDomainMarker.getModelIndex());
								chart.setProperty(prefix + PROPERTY_DOMAIN_MARKER_COLUMN_VALUE, vDomainMarker == null || vDomainMarker.getValue() == null ? "" : (Date)vDomainMarker.getValue()); 
								CollectableField vRangeMarker= cRangeMarker == null ? null : (CollectableField)tbl.getValueAt(j, cRangeMarker.getModelIndex());
								chart.setProperty(prefix + PROPERTY_RANGE_MARKER_COLUMN_VALUE, vRangeMarker == null || vRangeMarker.getValue() == null ? "" : (Number)vRangeMarker.getValue()); 
							}
							
							timeseriesdataset.addSeries(timeSeries);
						}
					}
				} catch (Exception e) {
					LOG.warn(e.getMessage(), e);
				}
				
				return new Dataset[]{timeseriesdataset};
	        }
			@Override
			public JFreeChart createChart(Chart chart) {
				XYDataset dataset = (XYDataset)createDataset(chart)[0];
				
				JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("", "", "",  dataset, false, false, false);
				jfreechart.getPlot().setDrawingSupplier(new DefaultDrawingSupplier());
				ChartUtilities.applyCurrentTheme(jfreechart);
			    
				setCommonChartProperties(chart, jfreechart);

				if (!(jfreechart.getPlot() instanceof XYPlot)) {
					return jfreechart;
				}
				
				// set properties for xy plot
				((XYPlot)jfreechart.getPlot()).setOrientation(
						chart.getProperty(PROPERTY_PLOT_ORIENTATION, String.class, PlotOrientation.HORIZONTAL.toString())
							.equals(PlotOrientation.VERTICAL.toString()) ? PlotOrientation.VERTICAL : PlotOrientation.HORIZONTAL);
				// set properties for domain axis
				((XYPlot)jfreechart.getPlot()).getDomainAxis().setLabel(chart.getProperty(PROPERTY_DOMAIN_LABEL, String.class));
				if (chart.getProperty(PROPERTY_DOMAIN_LABEL_FONT, Font.class) != null) {
					((XYPlot)jfreechart.getPlot()).getDomainAxis().setLabelFont(chart.getProperty(PROPERTY_DOMAIN_LABEL_FONT, Font.class));
				}
				((XYPlot)jfreechart.getPlot()).getDomainAxis().setLabelPaint(chart.getProperty(PROPERTY_DOMAIN_LABEL_COLOR, Color.class, Color.BLACK));
				if (chart.getProperty(PROPERTY_DOMAIN_TICK_LABELS_FONT, Font.class) != null) {
					((XYPlot)jfreechart.getPlot()).getDomainAxis().setTickLabelFont(chart.getProperty(PROPERTY_DOMAIN_TICK_LABELS_FONT, Font.class));
				}
				((XYPlot)jfreechart.getPlot()).getDomainAxis().setTickLabelsVisible(chart.getProperty(PROPERTY_DOMAIN_TICK_LABELS_VISIBLE, Boolean.class, Boolean.TRUE));
				((XYPlot)jfreechart.getPlot()).getDomainAxis().setTickMarksVisible(chart.getProperty(PROPERTY_DOMAIN_TICK_MARKS_VISIBLE, Boolean.class, Boolean.TRUE));
				// set properties for range axis
				((XYPlot)jfreechart.getPlot()).getRangeAxis().setLabel(chart.getProperty(PROPERTY_RANGE_LABEL, String.class));
				if (chart.getProperty(PROPERTY_RANGE_LABEL_FONT, Font.class) != null) {
					((XYPlot)jfreechart.getPlot()).getRangeAxis().setLabelFont(chart.getProperty(PROPERTY_RANGE_LABEL_FONT, Font.class));
				}
				((XYPlot)jfreechart.getPlot()).getRangeAxis().setLabelPaint(chart.getProperty(PROPERTY_RANGE_LABEL_COLOR, Color.class, Color.BLACK));
				if (chart.getProperty(PROPERTY_RANGE_TICK_LABELS_FONT, Font.class) != null) {
					((XYPlot)jfreechart.getPlot()).getRangeAxis().setTickLabelFont(chart.getProperty(PROPERTY_RANGE_TICK_LABELS_FONT, Font.class));
				}
				((XYPlot)jfreechart.getPlot()).getRangeAxis().setTickLabelsVisible(chart.getProperty(PROPERTY_RANGE_TICK_LABELS_VISIBLE, Boolean.class, Boolean.TRUE));
				((XYPlot)jfreechart.getPlot()).getRangeAxis().setTickMarksVisible(chart.getProperty(PROPERTY_RANGE_TICK_MARKS_VISIBLE, Boolean.class, Boolean.TRUE));
				((XYPlot)jfreechart.getPlot()).getRangeAxis().setAutoRange(chart.getProperty(PROPERTY_RANGE_AUTORANGE, Boolean.class, Boolean.TRUE));
				if (!((XYPlot)jfreechart.getPlot()).getRangeAxis().isAutoRange()) {
					((XYPlot)jfreechart.getPlot()).getRangeAxis().setLowerBound(chart.getProperty(PROPERTY_RANGE_LOWERBOUND, Double.class, 0D));
					((XYPlot)jfreechart.getPlot()).getRangeAxis().setUpperBound(chart.getProperty(PROPERTY_RANGE_UPPERBOUND, Double.class, 0D));
				}
				
				// set width and colors
				Object cItemWidth = chart.getProperty(PROPERTY_ITEMWIDTH_COLUMN_VALUE + ":" + 0, Number.class);
				if (cItemWidth instanceof Number) {
					if (((Number)cItemWidth).floatValue() > 0) {
						((AbstractRenderer)((XYPlot)jfreechart.getPlot()).getRenderer(0)).setSeriesStroke(0,
								new BasicStroke(((Number)cItemWidth).floatValue()));
					}
				}
				Color cItemColor = chart.getProperty(PROPERTY_ITEMCOLOR_COLUMN_VALUE + ":" + 0, Color.class);
				if (cItemColor != null) {
					((AbstractRenderer)((XYPlot)jfreechart.getPlot()).getRenderer(0)).setSeriesPaint(0, cItemColor);
				}
				
				// set markers.
				Object cDomainMarker = chart.getProperty(PROPERTY_DOMAIN_MARKER_COLUMN_VALUE, Date.class);
				if (cDomainMarker instanceof Date) {
					ValueMarker marker1 = new ValueMarker(((Date)cDomainMarker).getTime(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker1.setLabel(chart.getProperty(PROPERTY_DOMAIN_MARKER_LABEL, String.class));
					marker1.setLabelFont(new Font("Dialog", 0, 11));
					marker1.setLabelPaint(Color.DARK_GRAY);
					if (((XYPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.VERTICAL) {
						marker1.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
					} else {
						marker1.setLabelAnchor(RectangleAnchor.RIGHT);
						marker1.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
					}
					marker1.setLabelOffset(new RectangleInsets(2D, 5D, 2D, 5D));
					((XYPlot)jfreechart.getPlot()).addDomainMarker(marker1, Layer.BACKGROUND);
					ValueMarker marker2 = new ValueMarker(((Date)cDomainMarker).getTime(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker2.setLabel("" + CollectableFieldFormat.getInstance(Date.class).format(null, cDomainMarker));
					marker2.setLabelFont(new Font("Dialog", 0, 11));
					marker2.setLabelPaint(Color.DARK_GRAY);
					if (((XYPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.VERTICAL) {
						marker2.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(15D, 5D, 2D, 5D));
					} else {
						marker2.setLabelAnchor(RectangleAnchor.RIGHT);
						marker2.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(-25D, 5D, 2D, 5D));
					}
					((XYPlot)jfreechart.getPlot()).addDomainMarker(marker2, Layer.BACKGROUND);
				}
				Object cRangeMarker = chart.getProperty(PROPERTY_RANGE_MARKER_COLUMN_VALUE, Number.class);
				if (cRangeMarker != null && cRangeMarker instanceof Number) {
					ValueMarker marker1 = new ValueMarker(((Number)cRangeMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker1.setLabel(chart.getProperty(PROPERTY_RANGE_MARKER_LABEL, String.class));
					marker1.setLabelFont(new Font("Dialog", 0, 11));
					marker1.setLabelPaint(Color.DARK_GRAY);
					if (((XYPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.HORIZONTAL) {
						marker1.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
					} else {
						marker1.setLabelAnchor(RectangleAnchor.RIGHT);
						marker1.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
					}
					marker1.setLabelOffset(new RectangleInsets(2D, 5D, 2D, 5D));
					((XYPlot)jfreechart.getPlot()).addRangeMarker(marker1, Layer.BACKGROUND);
					ValueMarker marker2 = new ValueMarker(((Number)cRangeMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker2.setLabel("" + CollectableFieldFormat.getInstance(Double.class).format("#,##0.00", marker2.getValue()));
					marker2.setLabelFont(new Font("Dialog", 0, 11));
					marker2.setLabelPaint(Color.DARK_GRAY);	
					if (((XYPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.HORIZONTAL) {
						marker2.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(15D, 5D, 2D, 5D));
					} else {
						marker2.setLabelAnchor(RectangleAnchor.RIGHT);
						marker2.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(-25D, 5D, 2D, 5D));
					}
					((XYPlot)jfreechart.getPlot()).addRangeMarker(marker2, Layer.BACKGROUND);
				}

				if (chart.getProperty(PROPERTY_MOUSE_MARKER_SELECTED, Boolean.class, Boolean.FALSE)) {
					((XYPlot)jfreechart.getPlot()).getRenderer().setBaseToolTipGenerator(
							new StandardXYToolTipGenerator("{2}", SpringLocaleDelegate.getInstance().getDateFormat(), SpringLocaleDelegate.getInstance().getNumberFormat()));
				}
				if (chart.getProperty(PROPERTY_SHOW_MARKER_SELECTED, Boolean.class, Boolean.FALSE)) {
					((XYPlot)jfreechart.getPlot()).getRenderer().setBaseItemLabelGenerator(
							new StandardXYItemLabelGenerator("{2}", SpringLocaleDelegate.getInstance().getDateFormat(), SpringLocaleDelegate.getInstance().getNumberFormat()));
					((XYPlot)jfreechart.getPlot()).getRenderer().setBaseItemLabelsVisible(true);
				}

				return jfreechart;
			}
			@Override
			public List<Pair<String, String>> getChartProperties(Chart c, JFreeChart chart) {
				List<Pair<String, String>> result = new ArrayList<Pair<String,String>>();
				
				result.addAll(getCommonChartProperties(chart));

				if (!(chart.getPlot() instanceof XYPlot)) {
					return result;
				}
				
				// set properties for xy plot
				result.add(new Pair<String, String>(Chart.PROPERTY_PLOT_ORIENTATION, "" + ((XYPlot)chart.getPlot()).getOrientation().toString()));
				// set properties for domain axis
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_LABEL, "" + ((XYPlot)chart.getPlot()).getDomainAxis().getLabel()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_LABEL_FONT, "" + fontToString(((XYPlot)chart.getPlot()).getDomainAxis().getLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_LABEL_COLOR, "" + ((Color)((XYPlot)chart.getPlot()).getDomainAxis().getLabelPaint()).getRGB()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_TICK_LABELS_FONT, "" + fontToString(((XYPlot)chart.getPlot()).getDomainAxis().getTickLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_TICK_LABELS_VISIBLE, "" + ((XYPlot)chart.getPlot()).getDomainAxis().isTickLabelsVisible()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_TICK_MARKS_VISIBLE, "" + ((XYPlot)chart.getPlot()).getDomainAxis().isTickMarksVisible()));
				// set properties for range axis
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL, "" + ((XYPlot)chart.getPlot()).getRangeAxis().getLabel()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL_FONT, "" + fontToString(((XYPlot)chart.getPlot()).getRangeAxis().getLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL_COLOR, "" + ((Color)((XYPlot)chart.getPlot()).getRangeAxis().getLabelPaint()).getRGB()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_LABELS_FONT, "" + fontToString(((XYPlot)chart.getPlot()).getRangeAxis().getTickLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_LABELS_VISIBLE, "" + ((XYPlot)chart.getPlot()).getRangeAxis().isTickLabelsVisible()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_MARKS_VISIBLE, "" + ((XYPlot)chart.getPlot()).getRangeAxis().isTickMarksVisible()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_AUTORANGE, "" + ((XYPlot)chart.getPlot()).getRangeAxis().isAutoRange()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LOWERBOUND, "" + ((XYPlot)chart.getPlot()).getRangeAxis().getLowerBound()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_UPPERBOUND, "" + ((XYPlot)chart.getPlot()).getRangeAxis().getUpperBound()));
				
				return result;
			}			
			@Override
			public ChartColumn[] getValueColumnDesc(boolean withScaleColumn) {
				return new ChartColumn[] {
						new ChartColumn(Chart.PROPERTY_VALUE_COLUMN, Chart.PROPERTY_VALUE_COLUMN, Number.class)
				};
			}
			@Override
			public ChartColumn[] getDomainColumnDesc() {
				return new ChartColumn[] {
						new ChartColumn(Chart.PROPERTY_DOMAIN_COLUMN, Chart.PROPERTY_DOMAIN_COLUMN, Date.class),
						new ChartColumn(Chart.PROPERTY_ITEMWIDTH_COLUMN, Chart.PROPERTY_ITEMWIDTH_COLUMN, Number.class),
						new ChartColumn(Chart.PROPERTY_ITEMCOLOR_COLUMN, Chart.PROPERTY_ITEMCOLOR_COLUMN, String.class),
						new ChartColumn(Chart.PROPERTY_DOMAIN_MARKER_COLUMN, Chart.PROPERTY_DOMAIN_MARKER_COLUMN, Date.class),
						new ChartColumn(Chart.PROPERTY_RANGE_MARKER_COLUMN, Chart.PROPERTY_RANGE_MARKER_COLUMN, Number.class)
				};
			}		
			@Override
			public JTabbedPanel[] getCustomPlotEditorPanels(Chart chart, String prefix) {
				CustomChartEditor editor = new DefaultPlotMarkerEditor(chart, prefix);
				CustomChartEditor editorLegend = new DefaultLegendEditor(chart, prefix);
				return new JTabbedPanel[] {
						new JTabbedPanel(editor.getName(), editor),
						new JTabbedPanel(editorLegend.getName(), editorLegend)
				};
			}
		},
		XYSERIESCHART {
			@Override
			protected Dataset[] createDataset(Chart chart) {
				return createDataset(chart, "");
			}
			@Override
			protected Dataset[] createDataset(Chart chart, String prefix) {
				XYSeriesCollection xyseriesdataset = new XYSeriesCollection();
				
		       try {
					JTable tbl = chart.getSubForm().getJTable();

					TableColumn cValue = tbl.getColumn(chart.getProperty(prefix + PROPERTY_VALUE_COLUMN, UID.class));
					
					UID pDomainAxis = chart.getProperty(prefix + PROPERTY_DOMAIN_COLUMN, UID.class);
					TableColumn cDomainAxis = pDomainAxis == null ? null : tbl.getColumn(pDomainAxis);
					
					UID pItemWidth = chart.getProperty(prefix + PROPERTY_ITEMWIDTH_COLUMN, UID.class);
					TableColumn cItemWidth = pItemWidth == null ? null : tbl.getColumn(pItemWidth);
					UID pItemColor = chart.getProperty(prefix + PROPERTY_ITEMCOLOR_COLUMN, UID.class);
					TableColumn cItemColor = pItemColor == null ? null : tbl.getColumn(pItemColor);

					UID pDomainMarker = chart.getProperty(prefix + PROPERTY_DOMAIN_MARKER_COLUMN, UID.class);
					TableColumn cDomainMarker = pDomainMarker == null ? null : tbl.getColumn(pDomainMarker);
					UID pRangeMarker = chart.getProperty(prefix + PROPERTY_RANGE_MARKER_COLUMN, UID.class);
					TableColumn cRangeMarker = pRangeMarker == null ? null : tbl.getColumn(pRangeMarker);

					if (tbl.getModel() instanceof SearchConditionTableModelImpl) {
						return new Dataset[]{xyseriesdataset};
					}
					if (tbl.getColumnCount() > 0) {
						if (tbl.getRowCount() <= 0) {
							; // nothing
						} else {
							final XYSeries xySeries = new XYSeries("", false, true);
							
							for (int j = 0; j < tbl.getRowCount(); j++) {
								CollectableField vValue = (CollectableField)tbl.getValueAt(j, cValue.getModelIndex());
								CollectableField vXAxis = (CollectableField)tbl.getValueAt(j, cDomainAxis.getModelIndex());

								xySeries.addOrUpdate(vXAxis == null || vXAxis.getValue() == null ? 0 : (Number)vXAxis.getValue(),vValue == null || vValue.getValue() == null ? 0 : (Number)vValue.getValue());

								CollectableField vItemWidth= cItemWidth == null ? null : (CollectableField)tbl.getValueAt(j, cItemWidth.getModelIndex());
								chart.setProperty(prefix + PROPERTY_ITEMWIDTH_COLUMN_VALUE + ":" + 0, vItemWidth == null || vItemWidth.getValue() == null ? "" : (Number)vItemWidth.getValue());
								CollectableField vItemColor= cItemColor == null ? null : (CollectableField)tbl.getValueAt(j, cItemColor.getModelIndex());
								chart.setProperty(prefix + PROPERTY_ITEMCOLOR_COLUMN_VALUE + ":" + 0, vItemColor == null || vItemColor.getValue() == null ? "" : (String)vItemColor.getValue());

								CollectableField vDomainMarker= cDomainMarker == null ? null : (CollectableField)tbl.getValueAt(j, cDomainMarker.getModelIndex());
								chart.setProperty(prefix + PROPERTY_DOMAIN_MARKER_COLUMN_VALUE, vDomainMarker == null || vDomainMarker.getValue() == null ? "" : (String)vDomainMarker.getValue()); 
								CollectableField vRangeMarker= cRangeMarker == null ? null : (CollectableField)tbl.getValueAt(j, cRangeMarker.getModelIndex());
								chart.setProperty(prefix + PROPERTY_RANGE_MARKER_COLUMN_VALUE, vRangeMarker == null || vRangeMarker.getValue() == null ? "" : (Number)vRangeMarker.getValue());
							}
							
							xyseriesdataset.addSeries(xySeries);
						}
					}
				} catch (Exception e) {
					LOG.warn(e.getMessage(), e);
				}
				
				return new Dataset[]{xyseriesdataset};
	        }
			@Override
			public JFreeChart createChart(Chart chart) {
				XYDataset dataset = (XYDataset)createDataset(chart)[0];

				JFreeChart jfreechart = ChartFactory.createXYLineChart("", "", "",  dataset, PlotOrientation.VERTICAL, true, false, false);
				jfreechart.getPlot().setDrawingSupplier(new DefaultDrawingSupplier());
				ChartUtilities.applyCurrentTheme(jfreechart);
			    
				setCommonChartProperties(chart, jfreechart);

				if (!(jfreechart.getPlot() instanceof XYPlot)) {
					return jfreechart;
				}
				
				XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false) {
					@Override
					public LegendItem getLegendItem(int datasetIndex, int series) {
				        XYPlot plot = getPlot();
				        if (plot == null) {
				            return null;
				        }
				        LegendItem result = null;
				        XYDataset dataset = plot.getDataset(datasetIndex);
				        if (dataset != null) {
				            if (getItemVisible(series, 0)) {
				                String label = getLegendItemLabelGenerator().generateLabel(
				                        dataset, series);
				                String description = label;
				                String toolTipText = null;
				                if (getLegendItemToolTipGenerator() != null) {
				                    toolTipText = getLegendItemToolTipGenerator().generateLabel(
				                            dataset, series);
				                }
				                String urlText = null;
				                if (getLegendItemURLGenerator() != null) {
				                    urlText = getLegendItemURLGenerator().generateLabel(
				                            dataset, series);
				                }
				                boolean shapeIsVisible = getItemShapeVisible(series, 0);
				                Shape shape = lookupLegendShape(series);
				                boolean shapeIsFilled = getItemShapeFilled(series, 0);
				                Paint fillPaint = (getUseFillPaint()
				                    ? lookupSeriesFillPaint(series)
				                    : lookupSeriesPaint(series));
				                boolean shapeOutlineVisible = getDrawOutlines();
				                Paint outlinePaint = (getUseOutlinePaint()
				                    ? lookupSeriesOutlinePaint(series)
				                    : lookupSeriesPaint(series));
				                Stroke outlineStroke = lookupSeriesOutlineStroke(series);
				                boolean lineVisible = getItemLineVisible(series, 0);
				                Stroke lineStroke = new BasicStroke();
				                Paint linePaint = lookupSeriesPaint(series);
				                result = new LegendItem(label, description, toolTipText,
				                        urlText, shapeIsVisible, shape, shapeIsFilled,
				                        fillPaint, shapeOutlineVisible, outlinePaint,
				                        outlineStroke, lineVisible, getLegendLine(),
				                        lineStroke, linePaint);
				                result.setLabelFont(lookupLegendTextFont(series));
				                Paint labelPaint = lookupLegendTextPaint(series);
				                if (labelPaint != null) {
				                    result.setLabelPaint(labelPaint);
				                }
				                result.setSeriesKey(dataset.getSeriesKey(series));
				                result.setSeriesIndex(series);
				                result.setDataset(dataset);
				                result.setDatasetIndex(datasetIndex);
				            }
				        }
				        return result;
				    }
				};

				((XYPlot)jfreechart.getPlot()).setRenderer(renderer);
				
				final String legendLabel = chart.getProperty(PROPERTY_LEGEND_LABEL, String.class, "");
				renderer.setLegendItemLabelGenerator(
			    	      new StandardXYSeriesLabelGenerator() {
			    	    	  @Override
			    	    	  public String generateLabel(XYDataset dataset, int series) {
			    	    		  return legendLabel;
			    	    	  }
			    	        }
			    	    );
				
				// set properties for xy plot
				((XYPlot)jfreechart.getPlot()).setOrientation(
						chart.getProperty(PROPERTY_PLOT_ORIENTATION, String.class, PlotOrientation.HORIZONTAL.toString())
							.equals(PlotOrientation.VERTICAL.toString()) ? PlotOrientation.VERTICAL : PlotOrientation.HORIZONTAL);
				
				// set properties for domain axis
				((XYPlot)jfreechart.getPlot()).getDomainAxis().setLabel(chart.getProperty(PROPERTY_DOMAIN_LABEL, String.class));
				if (chart.getProperty(PROPERTY_DOMAIN_LABEL_FONT, Font.class) != null) {
					((XYPlot)jfreechart.getPlot()).getDomainAxis().setLabelFont(chart.getProperty(PROPERTY_DOMAIN_LABEL_FONT, Font.class));
				}
				((XYPlot)jfreechart.getPlot()).getDomainAxis().setLabelPaint(chart.getProperty(PROPERTY_DOMAIN_LABEL_COLOR, Color.class, Color.BLACK));
				if (chart.getProperty(PROPERTY_DOMAIN_TICK_LABELS_FONT, Font.class) != null) {
					((XYPlot)jfreechart.getPlot()).getDomainAxis().setTickLabelFont(chart.getProperty(PROPERTY_DOMAIN_TICK_LABELS_FONT, Font.class));
				}
				((XYPlot)jfreechart.getPlot()).getDomainAxis().setTickLabelsVisible(chart.getProperty(PROPERTY_DOMAIN_TICK_LABELS_VISIBLE, Boolean.class, Boolean.TRUE));
				((XYPlot)jfreechart.getPlot()).getDomainAxis().setTickMarksVisible(chart.getProperty(PROPERTY_DOMAIN_TICK_MARKS_VISIBLE, Boolean.class, Boolean.TRUE));
				((XYPlot)jfreechart.getPlot()).getDomainAxis().setAutoRange(chart.getProperty(PROPERTY_DOMAIN_AUTORANGE, Boolean.class, Boolean.TRUE));
				if (!((XYPlot)jfreechart.getPlot()).getDomainAxis().isAutoRange()) {
					((XYPlot)jfreechart.getPlot()).getDomainAxis().setLowerBound(chart.getProperty(PROPERTY_DOMAIN_LOWERBOUND, Double.class, 0D));
					((XYPlot)jfreechart.getPlot()).getDomainAxis().setUpperBound(chart.getProperty(PROPERTY_DOMAIN_UPPERBOUND, Double.class, 0D));
				}
				// set properties for range axis
				((XYPlot)jfreechart.getPlot()).getRangeAxis().setLabel(chart.getProperty(PROPERTY_RANGE_LABEL, String.class));
				if (chart.getProperty(PROPERTY_RANGE_LABEL_FONT, Font.class) != null) {
					((XYPlot)jfreechart.getPlot()).getRangeAxis().setLabelFont(chart.getProperty(PROPERTY_RANGE_LABEL_FONT, Font.class));
				}
				((XYPlot)jfreechart.getPlot()).getRangeAxis().setLabelPaint(chart.getProperty(PROPERTY_RANGE_LABEL_COLOR, Color.class, Color.BLACK));
				if (chart.getProperty(PROPERTY_RANGE_TICK_LABELS_FONT, Font.class) != null) {
					((XYPlot)jfreechart.getPlot()).getRangeAxis().setTickLabelFont(chart.getProperty(PROPERTY_RANGE_TICK_LABELS_FONT, Font.class));
				}
				((XYPlot)jfreechart.getPlot()).getRangeAxis().setTickLabelsVisible(chart.getProperty(PROPERTY_RANGE_TICK_LABELS_VISIBLE, Boolean.class, Boolean.TRUE));
				((XYPlot)jfreechart.getPlot()).getRangeAxis().setTickMarksVisible(chart.getProperty(PROPERTY_RANGE_TICK_MARKS_VISIBLE, Boolean.class, Boolean.TRUE));
				((XYPlot)jfreechart.getPlot()).getRangeAxis().setAutoRange(chart.getProperty(PROPERTY_RANGE_AUTORANGE, Boolean.class, Boolean.TRUE));
				if (!((XYPlot)jfreechart.getPlot()).getRangeAxis().isAutoRange()) {
					((XYPlot)jfreechart.getPlot()).getRangeAxis().setLowerBound(chart.getProperty(PROPERTY_RANGE_LOWERBOUND, Double.class, 0D));
					((XYPlot)jfreechart.getPlot()).getRangeAxis().setUpperBound(chart.getProperty(PROPERTY_RANGE_UPPERBOUND, Double.class, 0D));
				}
				
				// set width and colors
				Object cItemWidth = chart.getProperty(PROPERTY_ITEMWIDTH_COLUMN_VALUE + ":" + 0, Number.class);
				if (cItemWidth instanceof Number) {
					if (((Number)cItemWidth).floatValue() > 0) {
						((AbstractRenderer)((XYPlot)jfreechart.getPlot()).getRenderer(0)).setSeriesStroke(0,
								new BasicStroke(((Number)cItemWidth).floatValue()));
					}
				}
				Color cItemColor = chart.getProperty(PROPERTY_ITEMCOLOR_COLUMN_VALUE + ":" + 0, Color.class);
				if (cItemColor != null) {
					((AbstractRenderer)((XYPlot)jfreechart.getPlot()).getRenderer(0)).setSeriesPaint(0, cItemColor);
				}

				// set markers.
				Object cDomainMarker = chart.getProperty(PROPERTY_DOMAIN_MARKER_COLUMN_VALUE, Number.class);
				if (cDomainMarker instanceof Number) {
					ValueMarker marker1 = new ValueMarker(((Number)cDomainMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker1.setLabel(chart.getProperty(PROPERTY_DOMAIN_MARKER_LABEL, String.class));
					marker1.setLabelFont(new Font("Dialog", 0, 11));
					marker1.setLabelPaint(Color.DARK_GRAY);
					if (((XYPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.VERTICAL) {
						marker1.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
					} else {
						marker1.setLabelAnchor(RectangleAnchor.RIGHT);
						marker1.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
					}
					marker1.setLabelOffset(new RectangleInsets(2D, 5D, 2D, 5D));
					((XYPlot)jfreechart.getPlot()).addDomainMarker(marker1, Layer.BACKGROUND);
					ValueMarker marker2 = new ValueMarker(((Number)cDomainMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker2.setLabel("" + CollectableFieldFormat.getInstance(Double.class).format("#,##0.00", marker2.getValue()));
					marker2.setLabelFont(new Font("Dialog", 0, 11));
					marker2.setLabelPaint(Color.DARK_GRAY);
					if (((XYPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.VERTICAL) {
						marker2.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(15D, 5D, 2D, 5D));
					} else {
						marker2.setLabelAnchor(RectangleAnchor.RIGHT);
						marker2.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(-25D, 5D, 2D, 5D));
					}
					((XYPlot)jfreechart.getPlot()).addDomainMarker(marker2, Layer.BACKGROUND);
				}
				Object cRangeMarker = chart.getProperty(PROPERTY_RANGE_MARKER_COLUMN_VALUE, Number.class);
				if (cRangeMarker != null && cRangeMarker instanceof Number) {
					ValueMarker marker1 = new ValueMarker(((Number)cRangeMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker1.setLabel(chart.getProperty(PROPERTY_RANGE_MARKER_LABEL, String.class));
					marker1.setLabelFont(new Font("Dialog", 0, 11));
					marker1.setLabelPaint(Color.DARK_GRAY);
					if (((XYPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.HORIZONTAL) {
						marker1.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
					} else {
						marker1.setLabelAnchor(RectangleAnchor.RIGHT);
						marker1.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
					}
					marker1.setLabelOffset(new RectangleInsets(2D, 5D, 2D, 5D));
					((XYPlot)jfreechart.getPlot()).addRangeMarker(marker1, Layer.BACKGROUND);
					ValueMarker marker2 = new ValueMarker(((Number)cRangeMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker2.setLabel("" + CollectableFieldFormat.getInstance(Double.class).format("#,##0.00", marker2.getValue()));
					marker2.setLabelFont(new Font("Dialog", 0, 11));
					marker2.setLabelPaint(Color.DARK_GRAY);	
					if (((XYPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.HORIZONTAL) {
						marker2.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(15D, 5D, 2D, 5D));
					} else {
						marker2.setLabelAnchor(RectangleAnchor.RIGHT);
						marker2.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(-25D, 5D, 2D, 5D));
					}
					((XYPlot)jfreechart.getPlot()).addRangeMarker(marker2, Layer.BACKGROUND);
				}

				if (chart.getProperty(PROPERTY_MOUSE_MARKER_SELECTED, Boolean.class, Boolean.FALSE)) {
					renderer.setBaseToolTipGenerator(
							new StandardXYToolTipGenerator("{2}", SpringLocaleDelegate.getInstance().getDateFormat(), SpringLocaleDelegate.getInstance().getNumberFormat()));
				}
				if (chart.getProperty(PROPERTY_SHOW_MARKER_SELECTED, Boolean.class, Boolean.FALSE)) {
					renderer.setBaseItemLabelGenerator(
							new StandardXYItemLabelGenerator("{2}", SpringLocaleDelegate.getInstance().getDateFormat(), SpringLocaleDelegate.getInstance().getNumberFormat()));
					renderer.setBaseItemLabelsVisible(true);
				}
				
				return jfreechart;
			}
			@Override
			public List<Pair<String, String>> getChartProperties(Chart c, JFreeChart chart) {
				List<Pair<String, String>> result = new ArrayList<Pair<String,String>>();
				
				result.addAll(getCommonChartProperties(chart));

				if (!(chart.getPlot() instanceof XYPlot)) {
					return result;
				}
				
				// set properties for xy plot
				result.add(new Pair<String, String>(Chart.PROPERTY_PLOT_ORIENTATION, "" + ((XYPlot)chart.getPlot()).getOrientation().toString()));
				// set properties for domain axis
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_LABEL, "" + ((XYPlot)chart.getPlot()).getDomainAxis().getLabel()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_LABEL_FONT, "" + fontToString(((XYPlot)chart.getPlot()).getDomainAxis().getLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_LABEL_COLOR, "" + ((Color)((XYPlot)chart.getPlot()).getDomainAxis().getLabelPaint()).getRGB()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_TICK_LABELS_FONT, "" + fontToString(((XYPlot)chart.getPlot()).getDomainAxis().getTickLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_TICK_LABELS_VISIBLE, "" + ((XYPlot)chart.getPlot()).getDomainAxis().isTickLabelsVisible()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_TICK_MARKS_VISIBLE, "" + ((XYPlot)chart.getPlot()).getDomainAxis().isTickMarksVisible()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_AUTORANGE, "" + ((XYPlot)chart.getPlot()).getDomainAxis().isAutoRange()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_LOWERBOUND, "" + ((XYPlot)chart.getPlot()).getDomainAxis().getLowerBound()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_UPPERBOUND, "" + ((XYPlot)chart.getPlot()).getDomainAxis().getUpperBound()));
				// set properties for range axis
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL, "" + ((XYPlot)chart.getPlot()).getRangeAxis().getLabel()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL_FONT, "" + fontToString(((XYPlot)chart.getPlot()).getRangeAxis().getLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL_COLOR, "" + ((Color)((XYPlot)chart.getPlot()).getRangeAxis().getLabelPaint()).getRGB()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_LABELS_FONT, "" + fontToString(((XYPlot)chart.getPlot()).getRangeAxis().getTickLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_LABELS_VISIBLE, "" + ((XYPlot)chart.getPlot()).getRangeAxis().isTickLabelsVisible()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_MARKS_VISIBLE, "" + ((XYPlot)chart.getPlot()).getRangeAxis().isTickMarksVisible()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_AUTORANGE, "" + ((XYPlot)chart.getPlot()).getRangeAxis().isAutoRange()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LOWERBOUND, "" + ((XYPlot)chart.getPlot()).getRangeAxis().getLowerBound()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_UPPERBOUND, "" + ((XYPlot)chart.getPlot()).getRangeAxis().getUpperBound()));
				
				return result;
			}			
			@Override
			public ChartColumn[] getValueColumnDesc(boolean withScaleColumn) {
				return new ChartColumn[] {
						new ChartColumn(Chart.PROPERTY_VALUE_COLUMN, Chart.PROPERTY_VALUE_COLUMN, Number.class)
				};
			}
			@Override
			public ChartColumn[] getDomainColumnDesc() {
				return new ChartColumn[] {
						new ChartColumn(Chart.PROPERTY_DOMAIN_COLUMN, Chart.PROPERTY_DOMAIN_COLUMN, Number.class),
						new ChartColumn(Chart.PROPERTY_ITEMWIDTH_COLUMN, Chart.PROPERTY_ITEMWIDTH_COLUMN, Number.class),
						new ChartColumn(Chart.PROPERTY_ITEMCOLOR_COLUMN, Chart.PROPERTY_ITEMCOLOR_COLUMN, String.class),
						new ChartColumn(Chart.PROPERTY_DOMAIN_MARKER_COLUMN, Chart.PROPERTY_DOMAIN_MARKER_COLUMN, Number.class),
						new ChartColumn(Chart.PROPERTY_RANGE_MARKER_COLUMN, Chart.PROPERTY_RANGE_MARKER_COLUMN, Number.class)
				};
			}		
			@Override
			public JTabbedPanel[] getCustomPlotEditorPanels(Chart chart, String prefix) {
				CustomChartEditor editor = new DefaultPlotMarkerEditor(chart, prefix);
				CustomChartEditor editorLegend = new DefaultLegendEditor(chart, prefix);
				return new JTabbedPanel[] {
						new JTabbedPanel(editor.getName(), editor),
						new JTabbedPanel(editorLegend.getName(), editorLegend)
				};
			}
		},
		XYSCATTERCHART {
			@Override
			protected Dataset[] createDataset(Chart chart) {
				return createDataset(chart, "");
			}
			@Override
			protected Dataset[] createDataset(Chart chart, String prefix) {
				XYSeriesCollection xyseriesdataset = new XYSeriesCollection();
				
		       try {
					JTable tbl = chart.getSubForm().getJTable();

					TableColumn cValue = tbl.getColumn(chart.getProperty(prefix + PROPERTY_VALUE_COLUMN, UID.class));
					
					UID pDomainAxis = chart.getProperty(prefix + PROPERTY_DOMAIN_COLUMN, UID.class);
					TableColumn cDomainAxis = pDomainAxis == null ? null : tbl.getColumn(pDomainAxis);
					
					UID pItemWidth = chart.getProperty(prefix + PROPERTY_ITEMWIDTH_COLUMN, UID.class);
					TableColumn cItemWidth = pItemWidth == null ? null : tbl.getColumn(pItemWidth);
					UID pItemColor = chart.getProperty(prefix + PROPERTY_ITEMCOLOR_COLUMN, UID.class);
					TableColumn cItemColor = pItemColor == null ? null : tbl.getColumn(pItemColor);

					UID pDomainMarker = chart.getProperty(prefix + PROPERTY_DOMAIN_MARKER_COLUMN, UID.class);
					TableColumn cDomainMarker = pDomainMarker == null ? null : tbl.getColumn(pDomainMarker);
					UID pRangeMarker = chart.getProperty(prefix + PROPERTY_RANGE_MARKER_COLUMN, UID.class);
					TableColumn cRangeMarker = pRangeMarker == null ? null : tbl.getColumn(pRangeMarker);

					if (tbl.getModel() instanceof SearchConditionTableModelImpl) {
						return new Dataset[]{xyseriesdataset};
					}
					if (tbl.getColumnCount() > 0) {
						if (tbl.getRowCount() <= 0) {
							; // nothing
						} else {
							final XYSeries xySeries = new XYSeries("", false, true);
							
							for (int j = 0; j < tbl.getRowCount(); j++) {
								CollectableField vValue = (CollectableField)tbl.getValueAt(j, cValue.getModelIndex());
								CollectableField vXAxis = (CollectableField)tbl.getValueAt(j, cDomainAxis.getModelIndex());
								
								xySeries.addOrUpdate(vXAxis == null || vXAxis.getValue() == null ? 0 : (Number)vXAxis.getValue(),vValue == null || vValue.getValue() == null ? 0 : (Number)vValue.getValue());

								CollectableField vItemWidth= cItemWidth == null ? null : (CollectableField)tbl.getValueAt(j, cItemWidth.getModelIndex());
								chart.setProperty(prefix + PROPERTY_ITEMWIDTH_COLUMN_VALUE + ":" + 0, vItemWidth == null || vItemWidth.getValue() == null ? "" : (Number)vItemWidth.getValue());
								CollectableField vItemColor= cItemColor == null ? null : (CollectableField)tbl.getValueAt(j, cItemColor.getModelIndex());
								chart.setProperty(prefix + PROPERTY_ITEMCOLOR_COLUMN_VALUE + ":" + 0, vItemColor == null || vItemColor.getValue() == null ? "" : (String)vItemColor.getValue());

								CollectableField vDomainMarker= cDomainMarker == null ? null : (CollectableField)tbl.getValueAt(j, cDomainMarker.getModelIndex());
								chart.setProperty(prefix + PROPERTY_DOMAIN_MARKER_COLUMN_VALUE, vDomainMarker == null || vDomainMarker.getValue() == null ? "" : (Number)vDomainMarker.getValue()); 
								CollectableField vRangeMarker= cRangeMarker == null ? null : (CollectableField)tbl.getValueAt(j, cRangeMarker.getModelIndex());
								chart.setProperty(prefix + PROPERTY_RANGE_MARKER_COLUMN_VALUE, vRangeMarker == null || vRangeMarker.getValue() == null ? "" : (Number)vRangeMarker.getValue()); 
							}
							
							xyseriesdataset.addSeries(xySeries);
						}
					}
				} catch (Exception e) {
					LOG.warn(e.getMessage(), e);
				}
				
				return new Dataset[]{xyseriesdataset};
	        }
			@Override
			public JFreeChart createChart(Chart chart) {
				XYDataset dataset = (XYDataset)createDataset(chart)[0];

				JFreeChart jfreechart = ChartFactory.createScatterPlot("", "", "",  dataset, PlotOrientation.VERTICAL, true, false, false);
				jfreechart.getPlot().setDrawingSupplier(new DefaultDrawingSupplier());
				ChartUtilities.applyCurrentTheme(jfreechart);
			    
				setCommonChartProperties(chart, jfreechart);

				if (!(jfreechart.getPlot() instanceof XYPlot)) {
					return jfreechart;
				}
				
				XYItemRenderer renderer = new XYLineAndShapeRenderer(false, true) {
					@Override
					public LegendItem getLegendItem(int datasetIndex, int series) {
				        XYPlot plot = getPlot();
				        if (plot == null) {
				            return null;
				        }
				        LegendItem result = null;
				        XYDataset dataset = plot.getDataset(datasetIndex);
				        if (dataset != null) {
				            if (getItemVisible(series, 0)) {
				                String label = getLegendItemLabelGenerator().generateLabel(
				                        dataset, series);
				                String description = label;
				                String toolTipText = null;
				                if (getLegendItemToolTipGenerator() != null) {
				                    toolTipText = getLegendItemToolTipGenerator().generateLabel(
				                            dataset, series);
				                }
				                String urlText = null;
				                if (getLegendItemURLGenerator() != null) {
				                    urlText = getLegendItemURLGenerator().generateLabel(
				                            dataset, series);
				                }
				                boolean shapeIsVisible = getItemShapeVisible(series, 0);
				                Shape shape = lookupLegendShape(series);
				                boolean shapeIsFilled = getItemShapeFilled(series, 0);
				                Paint fillPaint = (getUseFillPaint()
				                    ? lookupSeriesFillPaint(series)
				                    : lookupSeriesPaint(series));
				                boolean shapeOutlineVisible = getDrawOutlines();
				                Paint outlinePaint = (getUseOutlinePaint()
				                    ? lookupSeriesOutlinePaint(series)
				                    : lookupSeriesPaint(series));
				                Stroke outlineStroke = lookupSeriesOutlineStroke(series);
				                boolean lineVisible = getItemLineVisible(series, 0);
				                Stroke lineStroke = new BasicStroke();
				                Paint linePaint = lookupSeriesPaint(series);
				                result = new LegendItem(label, description, toolTipText,
				                        urlText, shapeIsVisible, shape, shapeIsFilled,
				                        fillPaint, shapeOutlineVisible, outlinePaint,
				                        outlineStroke, lineVisible, getLegendLine(),
				                        lineStroke, linePaint);
				                result.setLabelFont(lookupLegendTextFont(series));
				                Paint labelPaint = lookupLegendTextPaint(series);
				                if (labelPaint != null) {
				                    result.setLabelPaint(labelPaint);
				                }
				                result.setSeriesKey(dataset.getSeriesKey(series));
				                result.setSeriesIndex(series);
				                result.setDataset(dataset);
				                result.setDatasetIndex(datasetIndex);
				            }
				        }
				        return result;
				    }
				};

				((XYPlot)jfreechart.getPlot()).setRenderer(renderer);
				
				final String legendLabel = chart.getProperty(PROPERTY_LEGEND_LABEL, String.class, "");
				renderer.setLegendItemLabelGenerator(
			    	      new StandardXYSeriesLabelGenerator() {
			    	    	  @Override
			    	    	  public String generateLabel(XYDataset dataset, int series) {
			    	    		  return legendLabel;
			    	    	  }
			    	        }
			    	    );
				
				// set properties for xy plot
				((XYPlot)jfreechart.getPlot()).setOrientation(
						chart.getProperty(PROPERTY_PLOT_ORIENTATION, String.class, PlotOrientation.HORIZONTAL.toString())
							.equals(PlotOrientation.VERTICAL.toString()) ? PlotOrientation.VERTICAL : PlotOrientation.HORIZONTAL);
				// set properties for domain axis
				((XYPlot)jfreechart.getPlot()).getDomainAxis().setLabel(chart.getProperty(PROPERTY_DOMAIN_LABEL, String.class));
				if (chart.getProperty(PROPERTY_DOMAIN_LABEL_FONT, Font.class) != null) {
					((XYPlot)jfreechart.getPlot()).getDomainAxis().setLabelFont(chart.getProperty(PROPERTY_DOMAIN_LABEL_FONT, Font.class));
				}
				((XYPlot)jfreechart.getPlot()).getDomainAxis().setLabelPaint(chart.getProperty(PROPERTY_DOMAIN_LABEL_COLOR, Color.class, Color.BLACK));
				if (chart.getProperty(PROPERTY_DOMAIN_TICK_LABELS_FONT, Font.class) != null) {
					((XYPlot)jfreechart.getPlot()).getDomainAxis().setTickLabelFont(chart.getProperty(PROPERTY_DOMAIN_TICK_LABELS_FONT, Font.class));
				}
				((XYPlot)jfreechart.getPlot()).getDomainAxis().setTickLabelsVisible(chart.getProperty(PROPERTY_DOMAIN_TICK_LABELS_VISIBLE, Boolean.class, Boolean.TRUE));
				((XYPlot)jfreechart.getPlot()).getDomainAxis().setTickMarksVisible(chart.getProperty(PROPERTY_DOMAIN_TICK_MARKS_VISIBLE, Boolean.class, Boolean.TRUE));
				((XYPlot)jfreechart.getPlot()).getDomainAxis().setAutoRange(chart.getProperty(PROPERTY_DOMAIN_AUTORANGE, Boolean.class, Boolean.TRUE));
				if (!((XYPlot)jfreechart.getPlot()).getDomainAxis().isAutoRange()) {
					((XYPlot)jfreechart.getPlot()).getDomainAxis().setLowerBound(chart.getProperty(PROPERTY_DOMAIN_LOWERBOUND, Double.class, 0D));
					((XYPlot)jfreechart.getPlot()).getDomainAxis().setUpperBound(chart.getProperty(PROPERTY_DOMAIN_UPPERBOUND, Double.class, 0D));
				}
				// set properties for range axis
				((XYPlot)jfreechart.getPlot()).getRangeAxis().setLabel(chart.getProperty(PROPERTY_RANGE_LABEL, String.class));
				if (chart.getProperty(PROPERTY_RANGE_LABEL_FONT, Font.class) != null) {
					((XYPlot)jfreechart.getPlot()).getRangeAxis().setLabelFont(chart.getProperty(PROPERTY_RANGE_LABEL_FONT, Font.class));
				}
				((XYPlot)jfreechart.getPlot()).getRangeAxis().setLabelPaint(chart.getProperty(PROPERTY_RANGE_LABEL_COLOR, Color.class, Color.BLACK));
				if (chart.getProperty(PROPERTY_RANGE_TICK_LABELS_FONT, Font.class) != null) {
					((XYPlot)jfreechart.getPlot()).getRangeAxis().setTickLabelFont(chart.getProperty(PROPERTY_RANGE_TICK_LABELS_FONT, Font.class));
				}
				((XYPlot)jfreechart.getPlot()).getRangeAxis().setTickLabelsVisible(chart.getProperty(PROPERTY_RANGE_TICK_LABELS_VISIBLE, Boolean.class, Boolean.TRUE));
				((XYPlot)jfreechart.getPlot()).getRangeAxis().setTickMarksVisible(chart.getProperty(PROPERTY_RANGE_TICK_MARKS_VISIBLE, Boolean.class, Boolean.TRUE));
				((XYPlot)jfreechart.getPlot()).getRangeAxis().setAutoRange(chart.getProperty(PROPERTY_RANGE_AUTORANGE, Boolean.class, Boolean.TRUE));
				if (!((XYPlot)jfreechart.getPlot()).getRangeAxis().isAutoRange()) {
					((XYPlot)jfreechart.getPlot()).getRangeAxis().setLowerBound(chart.getProperty(PROPERTY_RANGE_LOWERBOUND, Double.class, 0D));
					((XYPlot)jfreechart.getPlot()).getRangeAxis().setUpperBound(chart.getProperty(PROPERTY_RANGE_UPPERBOUND, Double.class, 0D));
				}
				
				// set width and colors
				Object cItemWidth = chart.getProperty(PROPERTY_ITEMWIDTH_COLUMN_VALUE + ":" + 0, Number.class);
				if (cItemWidth instanceof Number) {
					if (((Number)cItemWidth).floatValue() > 0) {
						((AbstractRenderer)((XYPlot)jfreechart.getPlot()).getRenderer(0)).setSeriesStroke(0,
								new BasicStroke(((Number)cItemWidth).floatValue()));
					}
				}
				Color cItemColor = chart.getProperty(PROPERTY_ITEMCOLOR_COLUMN_VALUE + ":" + 0, Color.class);
				if (cItemColor != null) {
					((AbstractRenderer)((XYPlot)jfreechart.getPlot()).getRenderer(0)).setSeriesPaint(0, cItemColor);
				}

				// set markers.
				Object cDomainMarker = chart.getProperty(PROPERTY_DOMAIN_MARKER_COLUMN_VALUE, Number.class);
				if (cDomainMarker instanceof Number) {
					ValueMarker marker1 = new ValueMarker(((Number)cDomainMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker1.setLabel(chart.getProperty(PROPERTY_DOMAIN_MARKER_LABEL, String.class));
					marker1.setLabelFont(new Font("Dialog", 0, 11));
					marker1.setLabelPaint(Color.DARK_GRAY);
					if (((XYPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.VERTICAL) {
						marker1.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
					} else {
						marker1.setLabelAnchor(RectangleAnchor.RIGHT);
						marker1.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
					}
					marker1.setLabelOffset(new RectangleInsets(2D, 5D, 2D, 5D));
					((XYPlot)jfreechart.getPlot()).addDomainMarker(marker1, Layer.BACKGROUND);
					ValueMarker marker2 = new ValueMarker(((Number)cDomainMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker2.setLabel("" + CollectableFieldFormat.getInstance(Double.class).format("#,##0.00", marker2.getValue()));
					marker2.setLabelFont(new Font("Dialog", 0, 11));
					marker2.setLabelPaint(Color.DARK_GRAY);
					if (((XYPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.VERTICAL) {
						marker2.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(15D, 5D, 2D, 5D));
					} else {
						marker2.setLabelAnchor(RectangleAnchor.RIGHT);
						marker2.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(-25D, 5D, 2D, 5D));
					}
					((XYPlot)jfreechart.getPlot()).addDomainMarker(marker2, Layer.BACKGROUND);
				}
				Object cRangeMarker = chart.getProperty(PROPERTY_RANGE_MARKER_COLUMN_VALUE, Number.class);
				if (cRangeMarker != null && cRangeMarker instanceof Number) {
					ValueMarker marker1 = new ValueMarker(((Number)cRangeMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker1.setLabel(chart.getProperty(PROPERTY_RANGE_MARKER_LABEL, String.class));
					marker1.setLabelFont(new Font("Dialog", 0, 11));
					marker1.setLabelPaint(Color.DARK_GRAY);
					if (((XYPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.HORIZONTAL) {
						marker1.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
					} else {
						marker1.setLabelAnchor(RectangleAnchor.RIGHT);
						marker1.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
					}
					marker1.setLabelOffset(new RectangleInsets(2D, 5D, 2D, 5D));
					((XYPlot)jfreechart.getPlot()).addRangeMarker(marker1, Layer.BACKGROUND);
					ValueMarker marker2 = new ValueMarker(((Number)cRangeMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker2.setLabel("" + CollectableFieldFormat.getInstance(Double.class).format("#,##0.00", marker2.getValue()));
					marker2.setLabelFont(new Font("Dialog", 0, 11));
					marker2.setLabelPaint(Color.DARK_GRAY);	
					if (((XYPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.HORIZONTAL) {
						marker2.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(15D, 5D, 2D, 5D));
					} else {
						marker2.setLabelAnchor(RectangleAnchor.RIGHT);
						marker2.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(-25D, 5D, 2D, 5D));
					}
					((XYPlot)jfreechart.getPlot()).addRangeMarker(marker2, Layer.BACKGROUND);
				}

				if (chart.getProperty(PROPERTY_MOUSE_MARKER_SELECTED, Boolean.class, Boolean.FALSE)) {
					renderer.setBaseToolTipGenerator(
							new StandardXYToolTipGenerator("{2}", SpringLocaleDelegate.getInstance().getDateFormat(), SpringLocaleDelegate.getInstance().getNumberFormat()));
				}
				if (chart.getProperty(PROPERTY_SHOW_MARKER_SELECTED, Boolean.class, Boolean.FALSE)) {
					renderer.setBaseItemLabelGenerator(
							new StandardXYItemLabelGenerator("{2}", SpringLocaleDelegate.getInstance().getDateFormat(), SpringLocaleDelegate.getInstance().getNumberFormat()));
					renderer.setBaseItemLabelsVisible(true);
				}
				
				return jfreechart;
			}
			@Override
			public List<Pair<String, String>> getChartProperties(Chart c, JFreeChart chart) {
				List<Pair<String, String>> result = new ArrayList<Pair<String,String>>();
				
				result.addAll(getCommonChartProperties(chart));

				if (!(chart.getPlot() instanceof XYPlot)) {
					return result;
				}
				
				// set properties for xy plot
				result.add(new Pair<String, String>(Chart.PROPERTY_PLOT_ORIENTATION, "" + ((XYPlot)chart.getPlot()).getOrientation().toString()));
				// set properties for domain axis
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_LABEL, "" + ((XYPlot)chart.getPlot()).getDomainAxis().getLabel()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_LABEL_FONT, "" + fontToString(((XYPlot)chart.getPlot()).getDomainAxis().getLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_LABEL_COLOR, "" + ((Color)((XYPlot)chart.getPlot()).getDomainAxis().getLabelPaint()).getRGB()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_TICK_LABELS_FONT, "" + fontToString(((XYPlot)chart.getPlot()).getDomainAxis().getTickLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_TICK_LABELS_VISIBLE, "" + ((XYPlot)chart.getPlot()).getDomainAxis().isTickLabelsVisible()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_TICK_MARKS_VISIBLE, "" + ((XYPlot)chart.getPlot()).getDomainAxis().isTickMarksVisible()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_AUTORANGE, "" + ((XYPlot)chart.getPlot()).getDomainAxis().isAutoRange()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_LOWERBOUND, "" + ((XYPlot)chart.getPlot()).getDomainAxis().getLowerBound()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_UPPERBOUND, "" + ((XYPlot)chart.getPlot()).getDomainAxis().getUpperBound()));
				// set properties for range axis
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL, "" + ((XYPlot)chart.getPlot()).getRangeAxis().getLabel()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL_FONT, "" + fontToString(((XYPlot)chart.getPlot()).getRangeAxis().getLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL_COLOR, "" + ((Color)((XYPlot)chart.getPlot()).getRangeAxis().getLabelPaint()).getRGB()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_LABELS_FONT, "" + fontToString(((XYPlot)chart.getPlot()).getRangeAxis().getTickLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_LABELS_VISIBLE, "" + ((XYPlot)chart.getPlot()).getRangeAxis().isTickLabelsVisible()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_MARKS_VISIBLE, "" + ((XYPlot)chart.getPlot()).getRangeAxis().isTickMarksVisible()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_AUTORANGE, "" + ((XYPlot)chart.getPlot()).getRangeAxis().isAutoRange()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LOWERBOUND, "" + ((XYPlot)chart.getPlot()).getRangeAxis().getLowerBound()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_UPPERBOUND, "" + ((XYPlot)chart.getPlot()).getRangeAxis().getUpperBound()));
				
				return result;
			}			
			@Override
			public ChartColumn[] getValueColumnDesc(boolean withScaleColumn) {
				return new ChartColumn[] {
						new ChartColumn(Chart.PROPERTY_VALUE_COLUMN, Chart.PROPERTY_VALUE_COLUMN, Number.class)
				};
			}
			@Override
			public ChartColumn[] getDomainColumnDesc() {
				return new ChartColumn[] {
						new ChartColumn(Chart.PROPERTY_DOMAIN_COLUMN, Chart.PROPERTY_DOMAIN_COLUMN, Number.class),
						new ChartColumn(Chart.PROPERTY_ITEMWIDTH_COLUMN, Chart.PROPERTY_ITEMWIDTH_COLUMN, Number.class),
						new ChartColumn(Chart.PROPERTY_ITEMCOLOR_COLUMN, Chart.PROPERTY_ITEMCOLOR_COLUMN, String.class),
						new ChartColumn(Chart.PROPERTY_DOMAIN_MARKER_COLUMN, Chart.PROPERTY_DOMAIN_MARKER_COLUMN, Number.class),
						new ChartColumn(Chart.PROPERTY_RANGE_MARKER_COLUMN, Chart.PROPERTY_RANGE_MARKER_COLUMN, Number.class)
				};
			}			
			@Override
			public JTabbedPanel[] getCustomPlotEditorPanels(Chart chart, String prefix) {
				CustomChartEditor editor = new DefaultPlotMarkerEditor(chart, prefix);
				CustomChartEditor editorLegend = new DefaultLegendEditor(chart, prefix);
				return new JTabbedPanel[] {
						new JTabbedPanel(editor.getName(), editor),
						new JTabbedPanel(editorLegend.getName(), editorLegend)
				};
			}
		},
		XYSTEPCHART {
			@Override
			protected Dataset[] createDataset(Chart chart) {
				return createDataset(chart, "");
			}
			@Override
			protected Dataset[] createDataset(Chart chart, String prefix) {
				XYSeriesCollection xyseriesdataset = new XYSeriesCollection();
				
		       try {
					JTable tbl = chart.getSubForm().getJTable();
					
					TableColumn cValue = tbl.getColumn(chart.getProperty(prefix + PROPERTY_VALUE_COLUMN, UID.class));
					
					UID pDomainAxis = chart.getProperty(prefix + PROPERTY_DOMAIN_COLUMN, UID.class);
					TableColumn cDomainAxis = pDomainAxis == null ? null : tbl.getColumn(pDomainAxis);
					
					UID pItemWidth = chart.getProperty(prefix + PROPERTY_ITEMWIDTH_COLUMN, UID.class);
					TableColumn cItemWidth = pItemWidth == null ? null : tbl.getColumn(pItemWidth);
					UID pItemColor = chart.getProperty(prefix + PROPERTY_ITEMCOLOR_COLUMN, UID.class);
					TableColumn cItemColor = pItemColor == null ? null : tbl.getColumn(pItemColor);

					UID pDomainMarker = chart.getProperty(prefix + PROPERTY_DOMAIN_MARKER_COLUMN, UID.class);
					TableColumn cDomainMarker = pDomainMarker == null ? null : tbl.getColumn(pDomainMarker);
					UID pRangeMarker = chart.getProperty(prefix + PROPERTY_RANGE_MARKER_COLUMN, UID.class);
					TableColumn cRangeMarker = pRangeMarker == null ? null : tbl.getColumn(pRangeMarker);

					if (tbl.getModel() instanceof SearchConditionTableModelImpl) {
						return new Dataset[]{xyseriesdataset};
					}
					if (tbl.getColumnCount() > 0) {
						if (tbl.getRowCount() <= 0) {
							; // nothing
						} else {
							final XYSeries xySeries = new XYSeries("", false, true);
							
							for (int j = 0; j < tbl.getRowCount(); j++) {
								CollectableField vValue = (CollectableField)tbl.getValueAt(j, cValue.getModelIndex());
								CollectableField vXAxis = (CollectableField)tbl.getValueAt(j, cDomainAxis.getModelIndex());

								xySeries.addOrUpdate(vXAxis == null || vXAxis.getValue() == null ? 0 : (Number)vXAxis.getValue(),vValue == null || vValue.getValue() == null ? 0 : (Number)vValue.getValue());

								CollectableField vItemWidth= cItemWidth == null ? null : (CollectableField)tbl.getValueAt(j, cItemWidth.getModelIndex());
								chart.setProperty(prefix + PROPERTY_ITEMWIDTH_COLUMN_VALUE + ":" + 0, vItemWidth == null || vItemWidth.getValue() == null ? "" : (Number)vItemWidth.getValue());
								CollectableField vItemColor= cItemColor == null ? null : (CollectableField)tbl.getValueAt(j, cItemColor.getModelIndex());
								chart.setProperty(prefix + PROPERTY_ITEMCOLOR_COLUMN_VALUE + ":" + 0, vItemColor == null || vItemColor.getValue() == null ? "" : (String)vItemColor.getValue());

								CollectableField vDomainMarker= cDomainMarker == null ? null : (CollectableField)tbl.getValueAt(j, cDomainMarker.getModelIndex());
								chart.setProperty(prefix + PROPERTY_DOMAIN_MARKER_COLUMN_VALUE, vDomainMarker == null || vDomainMarker.getValue() == null ? "" : (Number)vDomainMarker.getValue()); 
								CollectableField vRangeMarker= cRangeMarker == null ? null : (CollectableField)tbl.getValueAt(j, cRangeMarker.getModelIndex());
								chart.setProperty(prefix + PROPERTY_RANGE_MARKER_COLUMN_VALUE, vRangeMarker == null || vRangeMarker.getValue() == null ? "" : (Number)vRangeMarker.getValue()); 
							}
							
							xyseriesdataset.addSeries(xySeries);
						}
					}
				} catch (Exception e) {
					LOG.warn(e.getMessage(), e);
				}
				
				return new Dataset[]{xyseriesdataset};
	        }
			@Override
			public JFreeChart createChart(Chart chart) {
				XYDataset dataset = (XYDataset)createDataset(chart)[0];

				JFreeChart jfreechart = ChartFactory.createXYLineChart("", "", "",  dataset, PlotOrientation.VERTICAL, true, false, false);
				jfreechart.getPlot().setDrawingSupplier(new DefaultDrawingSupplier());
				ChartUtilities.applyCurrentTheme(jfreechart);
			    
				setCommonChartProperties(chart, jfreechart);

				if (!(jfreechart.getPlot() instanceof XYPlot)) {
					return jfreechart;
				}
				
				XYStepRenderer xysteprenderer = new XYStepRenderer() {
					@Override
					public LegendItem getLegendItem(int datasetIndex, int series) {
				        XYPlot plot = getPlot();
				        if (plot == null) {
				            return null;
				        }
				        LegendItem result = null;
				        XYDataset dataset = plot.getDataset(datasetIndex);
				        if (dataset != null) {
				            if (getItemVisible(series, 0)) {
				                String label = getLegendItemLabelGenerator().generateLabel(
				                        dataset, series);
				                String description = label;
				                String toolTipText = null;
				                if (getLegendItemToolTipGenerator() != null) {
				                    toolTipText = getLegendItemToolTipGenerator().generateLabel(
				                            dataset, series);
				                }
				                String urlText = null;
				                if (getLegendItemURLGenerator() != null) {
				                    urlText = getLegendItemURLGenerator().generateLabel(
				                            dataset, series);
				                }
				                boolean shapeIsVisible = getItemShapeVisible(series, 0);
				                Shape shape = lookupLegendShape(series);
				                boolean shapeIsFilled = getItemShapeFilled(series, 0);
				                Paint fillPaint = (getUseFillPaint()
				                    ? lookupSeriesFillPaint(series)
				                    : lookupSeriesPaint(series));
				                boolean shapeOutlineVisible = getDrawOutlines();
				                Paint outlinePaint = (getUseOutlinePaint()
				                    ? lookupSeriesOutlinePaint(series)
				                    : lookupSeriesPaint(series));
				                Stroke outlineStroke = lookupSeriesOutlineStroke(series);
				                boolean lineVisible = getItemLineVisible(series, 0);
				                Stroke lineStroke = new BasicStroke();
				                Paint linePaint = lookupSeriesPaint(series);
				                result = new LegendItem(label, description, toolTipText,
				                        urlText, shapeIsVisible, shape, shapeIsFilled,
				                        fillPaint, shapeOutlineVisible, outlinePaint,
				                        outlineStroke, lineVisible, getLegendLine(),
				                        lineStroke, linePaint);
				                result.setLabelFont(lookupLegendTextFont(series));
				                Paint labelPaint = lookupLegendTextPaint(series);
				                if (labelPaint != null) {
				                    result.setLabelPaint(labelPaint);
				                }
				                result.setSeriesKey(dataset.getSeriesKey(series));
				                result.setSeriesIndex(series);
				                result.setDataset(dataset);
				                result.setDatasetIndex(datasetIndex);
				            }
				        }
				        return result;
				    }
				};

				((XYPlot)jfreechart.getPlot()).setRenderer(xysteprenderer);
				
				final String legendLabel = chart.getProperty(PROPERTY_LEGEND_LABEL, String.class, "");
				xysteprenderer.setLegendItemLabelGenerator(
			    	      new StandardXYSeriesLabelGenerator() {
			    	    	  @Override
			    	    	  public String generateLabel(XYDataset dataset, int series) {
			    	    		  return legendLabel;
			    	    	  }
			    	        }
			    	    );

				xysteprenderer.setBaseShapesVisible(true);
				xysteprenderer.setSeriesStroke(0, new BasicStroke(2.0F));
				xysteprenderer.setSeriesStroke(1, new BasicStroke(2.0F));
				xysteprenderer.setDefaultEntityRadius(6);
				((XYPlot)jfreechart.getPlot()).setRenderer(xysteprenderer);
				
				// set properties for xy plot
				((XYPlot)jfreechart.getPlot()).setOrientation(
						chart.getProperty(PROPERTY_PLOT_ORIENTATION, String.class, PlotOrientation.HORIZONTAL.toString())
							.equals(PlotOrientation.VERTICAL.toString()) ? PlotOrientation.VERTICAL : PlotOrientation.HORIZONTAL);
				// set properties for domain axis
				((XYPlot)jfreechart.getPlot()).getDomainAxis().setLabel(chart.getProperty(PROPERTY_DOMAIN_LABEL, String.class));
				if (chart.getProperty(PROPERTY_DOMAIN_LABEL_FONT, Font.class) != null) {
					((XYPlot)jfreechart.getPlot()).getDomainAxis().setLabelFont(chart.getProperty(PROPERTY_DOMAIN_LABEL_FONT, Font.class));
				}
				((XYPlot)jfreechart.getPlot()).getDomainAxis().setLabelPaint(chart.getProperty(PROPERTY_DOMAIN_LABEL_COLOR, Color.class, Color.BLACK));
				if (chart.getProperty(PROPERTY_DOMAIN_TICK_LABELS_FONT, Font.class) != null) {
					((XYPlot)jfreechart.getPlot()).getDomainAxis().setTickLabelFont(chart.getProperty(PROPERTY_DOMAIN_TICK_LABELS_FONT, Font.class));
				}
				((XYPlot)jfreechart.getPlot()).getDomainAxis().setTickLabelsVisible(chart.getProperty(PROPERTY_DOMAIN_TICK_LABELS_VISIBLE, Boolean.class, Boolean.TRUE));
				((XYPlot)jfreechart.getPlot()).getDomainAxis().setTickMarksVisible(chart.getProperty(PROPERTY_DOMAIN_TICK_MARKS_VISIBLE, Boolean.class, Boolean.TRUE));
				((XYPlot)jfreechart.getPlot()).getDomainAxis().setAutoRange(chart.getProperty(PROPERTY_DOMAIN_AUTORANGE, Boolean.class, Boolean.TRUE));
				if (!((XYPlot)jfreechart.getPlot()).getDomainAxis().isAutoRange()) {
					((XYPlot)jfreechart.getPlot()).getDomainAxis().setLowerBound(chart.getProperty(PROPERTY_DOMAIN_LOWERBOUND, Double.class, 0D));
					((XYPlot)jfreechart.getPlot()).getDomainAxis().setUpperBound(chart.getProperty(PROPERTY_DOMAIN_UPPERBOUND, Double.class, 0D));
				}
				// set properties for range axis
				((XYPlot)jfreechart.getPlot()).getRangeAxis().setLabel(chart.getProperty(PROPERTY_RANGE_LABEL, String.class));
				if (chart.getProperty(PROPERTY_RANGE_LABEL_FONT, Font.class) != null) {
					((XYPlot)jfreechart.getPlot()).getRangeAxis().setLabelFont(chart.getProperty(PROPERTY_RANGE_LABEL_FONT, Font.class));
				}
				((XYPlot)jfreechart.getPlot()).getRangeAxis().setLabelPaint(chart.getProperty(PROPERTY_RANGE_LABEL_COLOR, Color.class, Color.BLACK));
				if (chart.getProperty(PROPERTY_RANGE_TICK_LABELS_FONT, Font.class) != null) {
					((XYPlot)jfreechart.getPlot()).getRangeAxis().setTickLabelFont(chart.getProperty(PROPERTY_RANGE_TICK_LABELS_FONT, Font.class));
				}
				((XYPlot)jfreechart.getPlot()).getRangeAxis().setTickLabelsVisible(chart.getProperty(PROPERTY_RANGE_TICK_LABELS_VISIBLE, Boolean.class, Boolean.TRUE));
				((XYPlot)jfreechart.getPlot()).getRangeAxis().setTickMarksVisible(chart.getProperty(PROPERTY_RANGE_TICK_MARKS_VISIBLE, Boolean.class, Boolean.TRUE));
				((XYPlot)jfreechart.getPlot()).getRangeAxis().setAutoRange(chart.getProperty(PROPERTY_RANGE_AUTORANGE, Boolean.class, Boolean.TRUE));
				if (!((XYPlot)jfreechart.getPlot()).getRangeAxis().isAutoRange()) {
					((XYPlot)jfreechart.getPlot()).getRangeAxis().setLowerBound(chart.getProperty(PROPERTY_RANGE_LOWERBOUND, Double.class, 0D));
					((XYPlot)jfreechart.getPlot()).getRangeAxis().setUpperBound(chart.getProperty(PROPERTY_RANGE_UPPERBOUND, Double.class, 0D));
				}
				
				// set width and colors
				Object cItemWidth = chart.getProperty(PROPERTY_ITEMWIDTH_COLUMN_VALUE + ":" + 0, Number.class);
				if (cItemWidth instanceof Number) {
					if (((Number)cItemWidth).floatValue() > 0) {
						((AbstractRenderer)((XYPlot)jfreechart.getPlot()).getRenderer(0)).setSeriesStroke(0,
								new BasicStroke(((Number)cItemWidth).floatValue()));
					}
				}
				Color cItemColor = chart.getProperty(PROPERTY_ITEMCOLOR_COLUMN_VALUE + ":" + 0, Color.class);
				if (cItemColor != null) {
					((AbstractRenderer)((XYPlot)jfreechart.getPlot()).getRenderer(0)).setSeriesPaint(0, cItemColor);
				}

				// set markers.
				Object cDomainMarker = chart.getProperty(PROPERTY_DOMAIN_MARKER_COLUMN_VALUE, Number.class);
				if (cDomainMarker instanceof Number) {
					ValueMarker marker1 = new ValueMarker(((Number)cDomainMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker1.setLabel(chart.getProperty(PROPERTY_DOMAIN_MARKER_LABEL, String.class));
					marker1.setLabelFont(new Font("Dialog", 0, 11));
					marker1.setLabelPaint(Color.DARK_GRAY);
					if (((XYPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.VERTICAL) {
						marker1.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
					} else {
						marker1.setLabelAnchor(RectangleAnchor.RIGHT);
						marker1.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
					}
					marker1.setLabelOffset(new RectangleInsets(2D, 5D, 2D, 5D));
					((XYPlot)jfreechart.getPlot()).addDomainMarker(marker1, Layer.BACKGROUND);
					ValueMarker marker2 = new ValueMarker(((Number)cDomainMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker2.setLabel("" + CollectableFieldFormat.getInstance(Double.class).format("#,##0.00", marker2.getValue()));
					marker2.setLabelFont(new Font("Dialog", 0, 11));
					marker2.setLabelPaint(Color.DARK_GRAY);
					if (((XYPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.VERTICAL) {
						marker2.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(15D, 5D, 2D, 5D));
					} else {
						marker2.setLabelAnchor(RectangleAnchor.RIGHT);
						marker2.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(-25D, 5D, 2D, 5D));
					}
					((XYPlot)jfreechart.getPlot()).addDomainMarker(marker2, Layer.BACKGROUND);
				}
				Object cRangeMarker = chart.getProperty(PROPERTY_RANGE_MARKER_COLUMN_VALUE, Number.class);
				if (cRangeMarker != null && cRangeMarker instanceof Number) {
					ValueMarker marker1 = new ValueMarker(((Number)cRangeMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker1.setLabel(chart.getProperty(PROPERTY_RANGE_MARKER_LABEL, String.class));
					marker1.setLabelFont(new Font("Dialog", 0, 11));
					marker1.setLabelPaint(Color.DARK_GRAY);
					if (((XYPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.HORIZONTAL) {
						marker1.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
					} else {
						marker1.setLabelAnchor(RectangleAnchor.RIGHT);
						marker1.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
					}
					marker1.setLabelOffset(new RectangleInsets(2D, 5D, 2D, 5D));
					((XYPlot)jfreechart.getPlot()).addRangeMarker(marker1, Layer.BACKGROUND);
					ValueMarker marker2 = new ValueMarker(((Number)cRangeMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
					marker2.setLabel("" + CollectableFieldFormat.getInstance(Double.class).format("#,##0.00", marker2.getValue()));
					marker2.setLabelFont(new Font("Dialog", 0, 11));
					marker2.setLabelPaint(Color.DARK_GRAY);	
					if (((XYPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.HORIZONTAL) {
						marker2.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(15D, 5D, 2D, 5D));
					} else {
						marker2.setLabelAnchor(RectangleAnchor.RIGHT);
						marker2.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
						marker2.setLabelOffset(new RectangleInsets(-25D, 5D, 2D, 5D));
					}
					((XYPlot)jfreechart.getPlot()).addRangeMarker(marker2, Layer.BACKGROUND);
				}
				
				if (chart.getProperty(PROPERTY_MOUSE_MARKER_SELECTED, Boolean.class, Boolean.FALSE)) {
					xysteprenderer.setBaseToolTipGenerator(
							new StandardXYToolTipGenerator("{2}", SpringLocaleDelegate.getInstance().getDateFormat(), SpringLocaleDelegate.getInstance().getNumberFormat()));
				}
				if (chart.getProperty(PROPERTY_SHOW_MARKER_SELECTED, Boolean.class, Boolean.FALSE)) {
					xysteprenderer.setBaseItemLabelGenerator(
							new StandardXYItemLabelGenerator("{2}", SpringLocaleDelegate.getInstance().getDateFormat(), SpringLocaleDelegate.getInstance().getNumberFormat()));
					xysteprenderer.setBaseItemLabelsVisible(true);
				}

				return jfreechart;
			}
			@Override
			public List<Pair<String, String>> getChartProperties(Chart c, JFreeChart chart) {
				List<Pair<String, String>> result = new ArrayList<Pair<String,String>>();
				
				result.addAll(getCommonChartProperties(chart));

				if (!(chart.getPlot() instanceof XYPlot)) {
					return result;
				}
				
				// set properties for xy plot
				result.add(new Pair<String, String>(Chart.PROPERTY_PLOT_ORIENTATION, "" + ((XYPlot)chart.getPlot()).getOrientation().toString()));
				// set properties for domain axis
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_LABEL, "" + ((XYPlot)chart.getPlot()).getDomainAxis().getLabel()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_LABEL_FONT, "" + fontToString(((XYPlot)chart.getPlot()).getDomainAxis().getLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_LABEL_COLOR, "" + ((Color)((XYPlot)chart.getPlot()).getDomainAxis().getLabelPaint()).getRGB()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_TICK_LABELS_FONT, "" + fontToString(((XYPlot)chart.getPlot()).getDomainAxis().getTickLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_TICK_LABELS_VISIBLE, "" + ((XYPlot)chart.getPlot()).getDomainAxis().isTickLabelsVisible()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_TICK_MARKS_VISIBLE, "" + ((XYPlot)chart.getPlot()).getDomainAxis().isTickMarksVisible()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_AUTORANGE, "" + ((XYPlot)chart.getPlot()).getDomainAxis().isAutoRange()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_LOWERBOUND, "" + ((XYPlot)chart.getPlot()).getDomainAxis().getLowerBound()));
				result.add(new Pair<String, String>(Chart.PROPERTY_DOMAIN_UPPERBOUND, "" + ((XYPlot)chart.getPlot()).getDomainAxis().getUpperBound()));
				// set properties for range axis
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL, "" + ((XYPlot)chart.getPlot()).getRangeAxis().getLabel()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL_FONT, "" + fontToString(((XYPlot)chart.getPlot()).getRangeAxis().getLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL_COLOR, "" + ((Color)((XYPlot)chart.getPlot()).getRangeAxis().getLabelPaint()).getRGB()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_LABELS_FONT, "" + fontToString(((XYPlot)chart.getPlot()).getRangeAxis().getTickLabelFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_LABELS_VISIBLE, "" + ((XYPlot)chart.getPlot()).getRangeAxis().isTickLabelsVisible()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_MARKS_VISIBLE, "" + ((XYPlot)chart.getPlot()).getRangeAxis().isTickMarksVisible()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_AUTORANGE, "" + ((XYPlot)chart.getPlot()).getRangeAxis().isAutoRange()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LOWERBOUND, "" + ((XYPlot)chart.getPlot()).getRangeAxis().getLowerBound()));
				result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_UPPERBOUND, "" + ((XYPlot)chart.getPlot()).getRangeAxis().getUpperBound()));
				
				return result;
			}			
			@Override
			public ChartColumn[] getValueColumnDesc(boolean withScaleColumn) {
				return new ChartColumn[] {
						new ChartColumn(Chart.PROPERTY_VALUE_COLUMN, Chart.PROPERTY_VALUE_COLUMN, Number.class)
				};
			}
			@Override
			public ChartColumn[] getDomainColumnDesc() {
				return new ChartColumn[] {
						new ChartColumn(Chart.PROPERTY_DOMAIN_COLUMN, Chart.PROPERTY_DOMAIN_COLUMN, Number.class),
						new ChartColumn(Chart.PROPERTY_ITEMWIDTH_COLUMN, Chart.PROPERTY_ITEMWIDTH_COLUMN, Number.class),
						new ChartColumn(Chart.PROPERTY_ITEMCOLOR_COLUMN, Chart.PROPERTY_ITEMCOLOR_COLUMN, String.class),
						new ChartColumn(Chart.PROPERTY_DOMAIN_MARKER_COLUMN, Chart.PROPERTY_DOMAIN_MARKER_COLUMN, Number.class),
						new ChartColumn(Chart.PROPERTY_RANGE_MARKER_COLUMN, Chart.PROPERTY_RANGE_MARKER_COLUMN, Number.class)
				};
			}			
			@Override
			public JTabbedPanel[] getCustomPlotEditorPanels(Chart chart, String prefix) {
				CustomChartEditor editor = new DefaultPlotMarkerEditor(chart, prefix);
				CustomChartEditor editorLegend = new DefaultLegendEditor(chart, prefix);
				return new JTabbedPanel[] {
						new JTabbedPanel(editor.getName(), editor),
						new JTabbedPanel(editorLegend.getName(), editorLegend)
				};
			}
		},
		DIALCHART {
			@Override
			protected Dataset[] createDataset(Chart chart) {
				DefaultValueDataset defaultvaluedataset = new DefaultValueDataset();
				
				try {
					JTable tbl = chart.getSubForm().getJTable();
					TableColumn cValue = tbl.getColumn(chart.getProperty(PROPERTY_VALUE_COLUMN, UID.class));
					
					TableColumn cLowerBound = tbl.getColumn(chart.getProperty(PROPERTY_RANGE_LOWERBOUND_COLUMN, UID.class));
					TableColumn cUpperBound = tbl.getColumn(chart.getProperty(PROPERTY_RANGE_UPPERBOUND_COLUMN, UID.class));
					
					UID pMajorTickIncrement = chart.getProperty(PROPERTY_RANGE_MAJORTICKINCREMENT_COLUMN, UID.class);
					TableColumn cMajorTickIncrement = pMajorTickIncrement == null ? null : tbl.getColumn(pMajorTickIncrement);
					UID pStandardRange = chart.getProperty(PROPERTY_RANGE_STANDARDRANGE_COLUMN, UID.class);
					TableColumn cStandardRange = pStandardRange == null ? null : tbl.getColumn(pStandardRange);
					UID pMediumRange = chart.getProperty(PROPERTY_RANGE_MEDIUMRANGE_COLUMN, UID.class);
					TableColumn cMediumRange = pMediumRange == null ? null : tbl.getColumn(pMediumRange);
					UID pCriticalRange = chart.getProperty(PROPERTY_RANGE_CRITICALRANGE_COLUMN, UID.class);
					TableColumn cCriticalRange = pCriticalRange == null ? null : tbl.getColumn(pCriticalRange);
					
					if (tbl.getModel() instanceof SearchConditionTableModelImpl) {
						defaultvaluedataset.setValue(0);
						return new Dataset[]{defaultvaluedataset};
					}
					if (tbl.getColumnCount() > 0) {
						if (tbl.getRowCount() <= 0) {
							defaultvaluedataset.setValue(0);
						} else {
							for (int j = 0; j < tbl.getRowCount(); j++) {
								CollectableField vLowerBound = (CollectableField)tbl.getValueAt(j, cLowerBound.getModelIndex());
								chart.setProperty(PROPERTY_RANGE_LOWERBOUND_COLUMN_VALUE, vLowerBound == null || vLowerBound.getValue() == null ? "" : "" + (Number)vLowerBound.getValue()); 
								CollectableField vUpperBound = (CollectableField)tbl.getValueAt(j, cUpperBound.getModelIndex());
								chart.setProperty(PROPERTY_RANGE_UPPERBOUND_COLUMN_VALUE, vUpperBound == null || vUpperBound.getValue() == null ? "" : "" + (Number)vUpperBound.getValue()); 
								CollectableField vMajorTickIncrement = cMajorTickIncrement == null ? null : (CollectableField)tbl.getValueAt(j, cMajorTickIncrement.getModelIndex());
								chart.setProperty(PROPERTY_RANGE_MAJORTICKINCREMENT_COLUMN_VALUE, vMajorTickIncrement == null || vMajorTickIncrement.getValue() == null ? "" : "" + (Number)vMajorTickIncrement.getValue()); 
								CollectableField vStandardRange = cStandardRange == null ? null : (CollectableField)tbl.getValueAt(j, cStandardRange.getModelIndex());
								chart.setProperty(PROPERTY_RANGE_STANDARDRANGE_COLUMN_VALUE, vStandardRange == null || vStandardRange.getValue() == null ? "" : (String)vStandardRange.getValue()); 
								CollectableField vMediumRange = cMediumRange == null ? null : (CollectableField)tbl.getValueAt(j, cMediumRange.getModelIndex());
								chart.setProperty(PROPERTY_RANGE_MEDIUMRANGE_COLUMN_VALUE, vMediumRange == null || vMediumRange.getValue() == null ? "" : (String)vMediumRange.getValue()); 
								CollectableField vCriticalRange= cCriticalRange == null ? null : (CollectableField)tbl.getValueAt(j, cCriticalRange.getModelIndex());
								chart.setProperty(PROPERTY_RANGE_CRITICALRANGE_COLUMN_VALUE, vCriticalRange == null || vCriticalRange.getValue() == null ? "" : (String)vCriticalRange.getValue()); 

								CollectableField vValue = (CollectableField)tbl.getValueAt(j, cValue.getModelIndex());
								defaultvaluedataset.setValue(vValue == null || vValue.getValue() == null ? 0 : (Number)vValue.getValue());
							}
						}
					}
				} catch (Exception e) {
					LOG.warn(e.getMessage(), e);
				}
				
				return new Dataset[]{defaultvaluedataset};
	        }
			@Override
			public JFreeChart createChart(Chart chart) {
				DialPlot dialplot = new DialPlot();

				ValueDataset dataset = (ValueDataset)createDataset(chart)[0];
				JFreeChart jfreechart = new JFreeChart("", dialplot);
				
				dialplot.setDataset(dataset);
				dialplot.setDialFrame(new StandardDialFrame());
				dialplot.setBackground(new DialBackground());
				
				ChartUtilities.applyCurrentTheme(jfreechart);
			    
				setCommonChartProperties(chart, jfreechart);

				if (!(jfreechart.getPlot() instanceof DialPlot)) {
					return jfreechart;
				}
				
				// set properties for dial plot
				DialTextAnnotation dialtextannotation = new DialTextAnnotation(chart.getProperty(PROPERTY_RANGE_LABEL, String.class, "")) {
					public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, Rectangle2D view) {
				        // work out the anchor point
				        Rectangle2D f = DialPlot.rectangleByRadius(frame, getRadius(),getRadius());
				        Arc2D arc = new Arc2D.Double(f, getAngle(), 0.0, Arc2D.OPEN);
				        Point2D pt = arc.getStartPoint();
				        g2.setPaint(getPaint());
				        g2.setFont(getFont());
				        
				        TextBlock block = TextUtilities.createTextBlock(getLabel(), getFont(), getPaint(),160, 2, new G2TextMeasurer(g2));
				        block.setLineAlignment(HorizontalAlignment.CENTER);
				        block.draw(g2, (float) pt.getX(), (float) pt.getY(), TextBlockAnchor.CENTER);
				    }
				};
				if (chart.getProperty(PROPERTY_RANGE_LABEL_FONT, Font.class) != null) {
					dialtextannotation.setFont(chart.getProperty(PROPERTY_RANGE_LABEL_FONT, Font.class));
				}
				dialtextannotation.setPaint(chart.getProperty(PROPERTY_RANGE_LABEL_COLOR, Color.class, Color.BLACK));
				dialtextannotation.setRadius(0.69999999999999996D);
				dialplot.addLayer(dialtextannotation);

				DialValueIndicator dialvalueindicator = new DialValueIndicator(0);
				dialplot.addLayer(dialvalueindicator);

				Double lowerBound = chart.getProperty(PROPERTY_RANGE_LOWERBOUND_COLUMN_VALUE, Double.class, 0D);
				Double upperBound = chart.getProperty(PROPERTY_RANGE_UPPERBOUND_COLUMN_VALUE, Double.class, 0D);
				Double majorTickIncrement = chart.getProperty(PROPERTY_RANGE_MAJORTICKINCREMENT_COLUMN_VALUE, Double.class, 10D);
				StandardDialScale standarddialscale = new StandardDialScale(lowerBound, upperBound, -120D, -300D, majorTickIncrement, 4);
				standarddialscale.setTickRadius(0.88D);
				standarddialscale.setTickLabelOffset(0.14999999999999999D);
				if (chart.getProperty(PROPERTY_RANGE_TICK_LABELS_FONT, Font.class) != null) {
					standarddialscale.setTickLabelFont(chart.getProperty(PROPERTY_RANGE_TICK_LABELS_FONT, Font.class));
				}
				standarddialscale.setTickLabelsVisible(chart.getProperty(PROPERTY_RANGE_TICK_LABELS_VISIBLE, Boolean.class, Boolean.TRUE));
				standarddialscale.setVisible(chart.getProperty(PROPERTY_RANGE_TICK_MARKS_VISIBLE, Boolean.class, Boolean.TRUE));
				dialplot.addScale(0, standarddialscale);
				
				dialplot.addPointer(new org.jfree.chart.plot.dial.DialPointer.Pin());
				
				DialCap dialcap = new DialCap();
				dialplot.setCap(dialcap);

				String cStandardRange = chart.getProperty(PROPERTY_RANGE_STANDARDRANGE_COLUMN_VALUE, String.class);
				String cMediumRange = chart.getProperty(PROPERTY_RANGE_MEDIUMRANGE_COLUMN_VALUE, String.class);
				String cCriticalRange = chart.getProperty(PROPERTY_RANGE_CRITICALRANGE_COLUMN_VALUE, String.class);

				if (cCriticalRange != null && cCriticalRange.indexOf(";") != -1) {
					String[] args = cCriticalRange.split(";");
					try {
						StandardDialRange standarddialrange = new StandardDialRange(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Color.red);
						standarddialrange.setInnerRadius(0.52000000000000002D);
						standarddialrange.setOuterRadius(0.55000000000000004D);
						dialplot.addLayer(standarddialrange);
					} catch (Exception e) {
						// ignore.
					}
				}
				if (cMediumRange != null && cMediumRange.indexOf(';') != -1) {
					String[] args = cMediumRange.split(";");
					try {
						StandardDialRange standarddialrange = new StandardDialRange(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Color.orange);
						standarddialrange.setInnerRadius(0.52000000000000002D);
						standarddialrange.setOuterRadius(0.55000000000000004D);
						dialplot.addLayer(standarddialrange);
					} catch (Exception e) {
						// ignore.
					}
				}
				if (cStandardRange != null && cStandardRange.indexOf(';') != -1) {
					String[] args = cStandardRange.split(";");
					try {
						StandardDialRange standarddialrange = new StandardDialRange(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Color.green);
						standarddialrange.setInnerRadius(0.52000000000000002D);
						standarddialrange.setOuterRadius(0.55000000000000004D);
						dialplot.addLayer(standarddialrange);
					} catch (Exception e) {
						// ignore.
					}
				}
				
				GradientPaint gradientpaint = new GradientPaint(new Point(), new Color(255, 255, 255), new Point(), new Color(170, 170, 220));
				DialBackground dialbackground = new DialBackground(gradientpaint);
				dialbackground.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL));
				dialplot.setBackground(dialbackground);
				dialplot.removePointer(0);
				org.jfree.chart.plot.dial.DialPointer.Pointer pointer = new org.jfree.chart.plot.dial.DialPointer.Pointer();
				dialplot.addPointer(pointer);

				return jfreechart;
			}
			@Override
			public List<Pair<String, String>> getChartProperties(Chart c, JFreeChart chart) {
				List<Pair<String, String>> result = new ArrayList<Pair<String,String>>();
				
				result.addAll(getCommonChartProperties(chart));

				if (!(chart.getPlot() instanceof DialPlot)) {
					return result;
				}
				
				// set properties for dial plot
				
				// this will be done by the custom editor itself
				
				return result;
			}			
			@Override
			public ChartColumn[] getValueColumnDesc(boolean withScaleColumn) {
				return new ChartColumn[] {
						new ChartColumn(Chart.PROPERTY_VALUE_COLUMN, Chart.PROPERTY_VALUE_COLUMN, Number.class)
				};
			}
			@Override
			public ChartColumn[] getDomainColumnDesc() {
				return new ChartColumn[] {
						new ChartColumn(Chart.PROPERTY_RANGE_LOWERBOUND_COLUMN, Chart.PROPERTY_RANGE_LOWERBOUND_COLUMN, Number.class),
						new ChartColumn(Chart.PROPERTY_RANGE_UPPERBOUND_COLUMN, Chart.PROPERTY_RANGE_UPPERBOUND_COLUMN, Number.class),
						new ChartColumn(Chart.PROPERTY_RANGE_MAJORTICKINCREMENT_COLUMN, Chart.PROPERTY_RANGE_MAJORTICKINCREMENT_COLUMN, Number.class),
						new ChartColumn(Chart.PROPERTY_RANGE_STANDARDRANGE_COLUMN, Chart.PROPERTY_RANGE_STANDARDRANGE_COLUMN, String.class),
						new ChartColumn(Chart.PROPERTY_RANGE_MEDIUMRANGE_COLUMN, Chart.PROPERTY_RANGE_MEDIUMRANGE_COLUMN, String.class),
						new ChartColumn(Chart.PROPERTY_RANGE_CRITICALRANGE_COLUMN, Chart.PROPERTY_RANGE_CRITICALRANGE_COLUMN, String.class)				
				};
			}
			
			class DefaultDialPlotEditor extends CustomChartEditor implements ActionListener {

			    private JTextField label;
			    private Font labelFont;
			    private PaintSample labelPaintSample;
			    private JTextField labelFontField;
			    private Font tickLabelFont;
			    private JTextField tickLabelFontField;
			    private JPanel slot1;
			    private JPanel slot2;
			    private JCheckBox showTickLabelsCheckBox;
			    private JCheckBox showTickMarksCheckBox;
			    private JTabbedPane otherTabs;
			    private ResourceBundle localizationResources
			            = ResourceBundleWrapper.getBundle(
			                    "org.jfree.chart.editor.LocalizationBundle");
			    public DefaultDialPlotEditor(Chart chart) {
			    	setName(localizationResources.getString("Range_Axis"));
			    	
			        this.labelFont = chart.getProperty(Chart.PROPERTY_RANGE_LABEL_FONT, Font.class, new Font("Dialog", 1, 12));
			        this.labelPaintSample = new PaintSample(chart.getProperty(Chart.PROPERTY_RANGE_LABEL_COLOR, Color.class, Color.BLACK));
			        this.tickLabelFont = chart.getProperty(Chart.PROPERTY_RANGE_TICK_LABELS_FONT, Font.class, new Font("Tahoma", 1, 12));

			        setLayout(new BorderLayout());

			        JPanel general = new JPanel(new BorderLayout());
			        general.setBorder(
			            BorderFactory.createTitledBorder(
			                BorderFactory.createEtchedBorder(),
			                localizationResources.getString("General")
			            )
			        );

			        JPanel interior = new JPanel(new LCBLayout(5));
			        interior.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
			        interior.add(new JLabel(localizationResources.getString("Label")));
			        this.label = new JTextField(chart.getProperty(Chart.PROPERTY_RANGE_LABEL, String.class));
			        interior.add(this.label);
			        interior.add(new JPanel());

			        interior.add(new JLabel(localizationResources.getString("Font")));
			        this.labelFontField = new FontDisplayField(this.labelFont);
			        interior.add(this.labelFontField);
			        JButton b = new JButton(localizationResources.getString("Select..."));
			        b.setActionCommand("SelectLabelFont");
			        b.addActionListener(this);
			        interior.add(b);

			        interior.add(new JLabel(localizationResources.getString("Paint")));
			        interior.add(this.labelPaintSample);
			        b = new JButton(localizationResources.getString("Select..."));
			        b.setActionCommand("SelectLabelPaint");
			        b.addActionListener(this);
			        interior.add(b);

			        general.add(interior);

			        add(general, BorderLayout.NORTH);

			        this.slot1 = new JPanel(new BorderLayout());

			        JPanel other = new JPanel(new BorderLayout());
			        other.setBorder(BorderFactory.createTitledBorder(
			                             BorderFactory.createEtchedBorder(),
			                             localizationResources.getString("Other")));

			        this.otherTabs = new JTabbedPane();
			        this.otherTabs.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

			        JPanel ticks = new JPanel(new LCBLayout(3));
			        ticks.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

			        this.showTickLabelsCheckBox = new JCheckBox(
			            localizationResources.getString("Show_tick_labels"),
			            chart.getProperty(Chart.PROPERTY_RANGE_TICK_LABELS_VISIBLE, Boolean.class, Boolean.TRUE)
			        );
			        ticks.add(this.showTickLabelsCheckBox);
			        ticks.add(new JPanel());
			        ticks.add(new JPanel());

			        ticks.add(
			            new JLabel(localizationResources.getString("Tick_label_font"))
			        );

			        this.tickLabelFontField = new FontDisplayField(this.tickLabelFont);
			        ticks.add(this.tickLabelFontField);
			        b = new JButton(localizationResources.getString("Select..."));
			        b.setActionCommand("SelectTickLabelFont");
			        b.addActionListener(this);
			        ticks.add(b);

			        this.showTickMarksCheckBox = new JCheckBox(
			            localizationResources.getString("Show_tick_marks"),
			    		chart.getProperty(Chart.PROPERTY_RANGE_TICK_MARKS_VISIBLE, Boolean.class, Boolean.TRUE)
			        );
			        ticks.add(this.showTickMarksCheckBox);
			        ticks.add(new JPanel());
			        ticks.add(new JPanel());

			        this.otherTabs.add(localizationResources.getString("Ticks"), ticks);

			        other.add(this.otherTabs);

			        this.slot1.add(other);

			        this.slot2 = new JPanel(new BorderLayout());
			        this.slot2.add(this.slot1, BorderLayout.NORTH);
			        add(this.slot2);
			    }
			    public void actionPerformed(ActionEvent event) {
			        String command = event.getActionCommand();
			        if (command.equals("SelectLabelFont")) {
			            attemptLabelFontSelection();
			        }
			        else if (command.equals("SelectLabelPaint")) {
			            attemptModifyLabelPaint();
			        }
			        else if (command.equals("SelectTickLabelFont")) {
			            attemptTickLabelFontSelection();
			        }
			    }
			    private void attemptLabelFontSelection() {
			        FontChooserPanel panel = new FontChooserPanel(this.labelFont);
			        int result = JOptionPane.showConfirmDialog(this, panel,
			            localizationResources.getString("Font_Selection"),
			            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

			        if (result == JOptionPane.OK_OPTION) {
			            this.labelFont = panel.getSelectedFont();
			            this.labelFontField.setText(
			                this.labelFont.getFontName() + " " + this.labelFont.getSize()
			            );
			        }
			    }
			    private void attemptModifyLabelPaint() {
			        Color c;
			        c = JColorChooser.showDialog(
			            this, localizationResources.getString("Label_Color"), Color.blue
			        );
			        if (c != null) {
			            this.labelPaintSample.setPaint(c);
			        }
			    }
			    public void attemptTickLabelFontSelection() {
			        FontChooserPanel panel = new FontChooserPanel(this.tickLabelFont);
			        int result = JOptionPane.showConfirmDialog(this, panel,
			            localizationResources.getString("Font_Selection"),
			            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

			        if (result == JOptionPane.OK_OPTION) {
			            this.tickLabelFont = panel.getSelectedFont();
			            this.tickLabelFontField.setText(
			                this.tickLabelFont.getFontName() + " "
			                + this.tickLabelFont.getSize()
			            );
			        }
			    }
				@Override
			    public List<Pair<String, String>> applyChartProperties(JFreeChart chart) {
					List<Pair<String, String>> result = new ArrayList<Pair<String,String>>();

					result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL, "" + this.label.getText()));
					result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL_FONT, "" + Chart.fontToString(this.labelFont)));
					result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL_COLOR, "" + ((Color)this.labelPaintSample.getPaint()).getRGB()));
			    	
					result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_LABELS_FONT, "" + Chart.fontToString(this.tickLabelFont)));
					result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_LABELS_VISIBLE, "" + this.showTickLabelsCheckBox.isSelected()));
					result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_MARKS_VISIBLE, "" + this.showTickMarksCheckBox.isSelected()));
					
					return result;
			    }
			}
			@Override
			public JTabbedPanel[] getCustomPlotEditorPanels(Chart chart, String prefix) {
				CustomChartEditor editor = new DefaultDialPlotEditor(chart);
				return new JTabbedPanel[] {
						new JTabbedPanel(editor.getName(), editor)
				};
			}
		},
		DIALCHART2 {
			@Override
			protected Dataset[] createDataset(Chart chart) {
				DefaultValueDataset defaultvaluedataset1 = new DefaultValueDataset();
				DefaultValueDataset defaultvaluedataset2 = new DefaultValueDataset();
				
				try {
					JTable tbl = chart.getSubForm().getJTable();
					
					TableColumn cValueInner = tbl.getColumn(chart.getProperty(PROPERTY_VALUE_COLUMN_INNER, UID.class));					
					TableColumn cLowerBoundInner = tbl.getColumn(chart.getProperty(PROPERTY_RANGE_LOWERBOUND_COLUMN_INNER, UID.class));
					TableColumn cUpperBoundInner = tbl.getColumn(chart.getProperty(PROPERTY_RANGE_UPPERBOUND_COLUMN_INNER, UID.class));
					UID pMajorTickIncrementInner = chart.getProperty(PROPERTY_RANGE_MAJORTICKINCREMENT_COLUMN_INNER, UID.class);
					TableColumn cMajorTickIncrementInner = pMajorTickIncrementInner == null ? null : tbl.getColumn(pMajorTickIncrementInner);
					
					TableColumn cValueOuter = tbl.getColumn(chart.getProperty(PROPERTY_VALUE_COLUMN_OUTER, UID.class));					
					TableColumn cLowerBoundOuter = tbl.getColumn(chart.getProperty(PROPERTY_RANGE_LOWERBOUND_COLUMN_OUTER, UID.class));
					TableColumn cUpperBoundOuter = tbl.getColumn(chart.getProperty(PROPERTY_RANGE_UPPERBOUND_COLUMN_OUTER, UID.class));
					UID pMajorTickIncrementOuter = chart.getProperty(PROPERTY_RANGE_MAJORTICKINCREMENT_COLUMN_OUTER, UID.class);
					TableColumn cMajorTickIncrementOuter = pMajorTickIncrementOuter == null ? null : tbl.getColumn(pMajorTickIncrementOuter);

					if (tbl.getModel() instanceof SearchConditionTableModelImpl) {
						defaultvaluedataset1.setValue(0);
						defaultvaluedataset2.setValue(0);
						return new Dataset[]{defaultvaluedataset1, defaultvaluedataset2};
					}
					if (tbl.getColumnCount() > 0) {
						if (tbl.getRowCount() <= 0) {
							defaultvaluedataset1.setValue(0);
							defaultvaluedataset2.setValue(0);
						} else {
							for (int j = 0; j < tbl.getRowCount(); j++) {
								CollectableField vLowerBoundInner = (CollectableField)tbl.getValueAt(j, cLowerBoundInner.getModelIndex());
								chart.setProperty(PROPERTY_RANGE_LOWERBOUND_COLUMN_VALUE_INNER, vLowerBoundInner == null || vLowerBoundInner.getValue() == null ? "" : "" + (Number)vLowerBoundInner.getValue()); 
								CollectableField vUpperBoundInner = (CollectableField)tbl.getValueAt(j, cUpperBoundInner.getModelIndex());
								chart.setProperty(PROPERTY_RANGE_UPPERBOUND_COLUMN_VALUE_INNER, vUpperBoundInner == null || vUpperBoundInner.getValue() == null ? "" : "" + (Number)vUpperBoundInner.getValue()); 
								CollectableField vMajorTickIncrementInner = cMajorTickIncrementInner == null ? null : (CollectableField)tbl.getValueAt(j, cMajorTickIncrementInner.getModelIndex());
								chart.setProperty(PROPERTY_RANGE_MAJORTICKINCREMENT_COLUMN_VALUE_INNER, vMajorTickIncrementInner == null || vMajorTickIncrementInner.getValue() == null ? "" : "" + (Number)vMajorTickIncrementInner.getValue()); 
								CollectableField vLowerBoundOuter = (CollectableField)tbl.getValueAt(j, cLowerBoundOuter.getModelIndex());
								chart.setProperty(PROPERTY_RANGE_LOWERBOUND_COLUMN_VALUE_OUTER, vLowerBoundOuter == null || vLowerBoundOuter.getValue() == null ? "" : "" + (Number)vLowerBoundOuter.getValue()); 
								CollectableField vUpperBoundOuter = (CollectableField)tbl.getValueAt(j, cUpperBoundOuter.getModelIndex());
								chart.setProperty(PROPERTY_RANGE_UPPERBOUND_COLUMN_VALUE_OUTER, vUpperBoundOuter == null || vUpperBoundOuter.getValue() == null ? "" : "" + (Number)vUpperBoundOuter.getValue()); 
								CollectableField vMajorTickIncrementOuter = cMajorTickIncrementOuter == null ? null : (CollectableField)tbl.getValueAt(j, cMajorTickIncrementOuter.getModelIndex());
								chart.setProperty(PROPERTY_RANGE_MAJORTICKINCREMENT_COLUMN_VALUE_OUTER, vMajorTickIncrementOuter == null || vMajorTickIncrementOuter.getValue() == null ? "" : "" + (Number)vMajorTickIncrementOuter.getValue()); 

								CollectableField vValueOuter = (CollectableField)tbl.getValueAt(j, cValueOuter.getModelIndex());
								CollectableField vValueInner = (CollectableField)tbl.getValueAt(j, cValueInner.getModelIndex());
								defaultvaluedataset1.setValue(vValueOuter == null || vValueOuter.getValue() == null ? 0 : (Number)vValueOuter.getValue());
								defaultvaluedataset2.setValue(vValueInner == null || vValueInner.getValue() == null ? 0 : (Number)vValueInner.getValue());
							}
						}
					}
				} catch (Exception e) {
					LOG.warn(e.getMessage(), e);
				}

				return new Dataset[]{defaultvaluedataset1, defaultvaluedataset2};
	        }
			@Override
			public JFreeChart createChart(Chart chart) {
				DialPlot dialplot = new DialPlot();

				Dataset[] dataset = createDataset(chart);
				JFreeChart jfreechart = new JFreeChart("", dialplot);

				dialplot.setView(0.0D, 0.0D, 1.0D, 1.0D);
				dialplot.setDataset(0, (ValueDataset)dataset[0]);
				dialplot.setDataset(1, (ValueDataset)dataset[1]);

				StandardDialFrame standarddialframe = new StandardDialFrame();
				standarddialframe.setBackgroundPaint(Color.lightGray);
				standarddialframe.setForegroundPaint(Color.darkGray);
				dialplot.setDialFrame(standarddialframe);

				GradientPaint gradientpaint = new GradientPaint(new Point(), new Color(255, 255, 255), new Point(), new Color(170, 170, 220));
				DialBackground dialbackground = new DialBackground(gradientpaint);
				dialbackground.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL));
				dialplot.setBackground(dialbackground);
				
				ChartUtilities.applyCurrentTheme(jfreechart);
			    
				setCommonChartProperties(chart, jfreechart);

				if (!(jfreechart.getPlot() instanceof DialPlot)) {
					return jfreechart;
				}
				
				// set properties for dial plot
				DialTextAnnotation dialtextannotation = new DialTextAnnotation(chart.getProperty(PROPERTY_RANGE_LABEL, String.class, "")) {
					public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, Rectangle2D view) {
				        // work out the anchor point
				        Rectangle2D f = DialPlot.rectangleByRadius(frame, getRadius(),getRadius());
				        Arc2D arc = new Arc2D.Double(f, getAngle(), 0.0, Arc2D.OPEN);
				        Point2D pt = arc.getStartPoint();
				        g2.setPaint(getPaint());
				        g2.setFont(getFont());
				        
				        TextBlock block = TextUtilities.createTextBlock(getLabel(), getFont(), getPaint(),160, 2, new G2TextMeasurer(g2));
				        block.setLineAlignment(HorizontalAlignment.CENTER);
				        block.draw(g2, (float) pt.getX(), (float) pt.getY(), TextBlockAnchor.CENTER);
				    }
				};
				if (chart.getProperty(PROPERTY_RANGE_LABEL_FONT, Font.class) != null) {
					dialtextannotation.setFont(chart.getProperty(PROPERTY_RANGE_LABEL_FONT, Font.class));
				}
				dialtextannotation.setPaint(chart.getProperty(PROPERTY_RANGE_LABEL_COLOR, Color.class, Color.BLACK));
				dialtextannotation.setRadius(0.69999999999999996D);
				dialplot.addLayer(dialtextannotation);
				
				final DialValueIndicator dialvalueindicator1 = new DialValueIndicator(0);
				dialvalueindicator1.setFont(new Font("Dialog", 0, 10));
				dialvalueindicator1.setOutlinePaint(Color.darkGray);
				dialvalueindicator1.setRadius(0.59999999999999998D);
				dialvalueindicator1.setAngle(-103D);
				dialplot.addLayer(dialvalueindicator1);
				DialTextAnnotation dialtextannotation1 = new DialTextAnnotation(chart.getProperty(PROPERTY_RANGE_LABEL_INNER, String.class, "")) {
					public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, Rectangle2D view) {
				        // work out the anchor point
				        Rectangle2D f = DialPlot.rectangleByRadius(frame, getRadius(), getRadius());
				        Arc2D arc = new Arc2D.Double(f, getAngle(), 0.0, Arc2D.OPEN);
				        Point2D pt = arc.getStartPoint();
				        g2.setPaint(getPaint());
				        g2.setFont(getFont());
				        
				        FontMetrics fm = g2.getFontMetrics(dialvalueindicator1.getFont());
				        String valueStr = dialvalueindicator1.getNumberFormat().format(dialvalueindicator1.getTemplateValue());
				        Rectangle2D tb = TextUtilities.getTextBounds(valueStr, g2, fm);

				        FontMetrics fm2 = g2.getFontMetrics(getFont());
				        Rectangle2D tb2 = TextUtilities.getTextBounds(getLabel(), g2, fm2);
				        
				        TextBlock block = TextUtilities.createTextBlock(getLabel(), getFont(), getPaint(), (float)(tb.getWidth() * 1.5), 2, new G2TextMeasurer(g2));
				        block.setLineAlignment(HorizontalAlignment.CENTER);
				        
				        block.draw(g2, (float) (pt.getX() - tb.getWidth() * 0.7 + 2),
				          (float) (pt.getY() - (tb.getHeight() + tb2.getHeight() + 12)), null);
				    }
				};
				dialtextannotation1.setFont(new Font("Dialog", 1, 10));
				dialtextannotation1.setRadius(0.59999999999999998D);
				dialtextannotation1.setAngle(-103D);
				dialtextannotation1.setPaint(dialvalueindicator1.getOutlinePaint());
				dialplot.addLayer(dialtextannotation1);

				final DialValueIndicator dialvalueindicator2 = new DialValueIndicator(1);
				dialvalueindicator2.setFont(new Font("Dialog", 0, 10));
				dialvalueindicator2.setOutlinePaint(Color.red);
				dialvalueindicator2.setRadius(0.59999999999999998D);
				dialvalueindicator2.setAngle(-77D);
				dialplot.addLayer(dialvalueindicator2);
				DialTextAnnotation dialtextannotation2 = new DialTextAnnotation(chart.getProperty(PROPERTY_RANGE_LABEL_OUTER, String.class, "")) {
					public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, Rectangle2D view) {
				        // work out the anchor point
				        Rectangle2D f = DialPlot.rectangleByRadius(frame, getRadius(), getRadius());
				        Arc2D arc = new Arc2D.Double(f, getAngle(), 0.0, Arc2D.OPEN);
				        Point2D pt = arc.getStartPoint();
				        g2.setPaint(getPaint());
				        g2.setFont(getFont());
				        
				        FontMetrics fm = g2.getFontMetrics(dialvalueindicator2.getFont());
				        String valueStr = dialvalueindicator2.getNumberFormat().format(dialvalueindicator2.getTemplateValue());
				        Rectangle2D tb = TextUtilities.getTextBounds(valueStr, g2, fm);

				        FontMetrics fm2 = g2.getFontMetrics(getFont());
				        Rectangle2D tb2 = TextUtilities.getTextBounds(getLabel(), g2, fm2);
				        
				        TextBlock block = TextUtilities.createTextBlock(getLabel(), getFont(), getPaint(), (float)(tb.getWidth() * 1.5), 2, new G2TextMeasurer(g2));
				        block.setLineAlignment(HorizontalAlignment.CENTER);
				        
				        block.draw(g2, (float) (pt.getX() - tb.getWidth() * 0.7 + 2),
				          (float) (pt.getY() - (tb.getHeight() + tb2.getHeight() + 12)), null);
				    }
				};
				dialtextannotation2.setFont(new Font("Dialog", 1, 10));
				dialtextannotation2.setRadius(0.59999999999999998D);
				dialtextannotation2.setAngle(-77D);
				dialtextannotation2.setPaint(dialvalueindicator2.getOutlinePaint());
				dialplot.addLayer(dialtextannotation2);
				
				Double lowerBound1 = chart.getProperty(PROPERTY_RANGE_LOWERBOUND_COLUMN_VALUE_OUTER, Double.class, 0D);
				Double upperBound1 = chart.getProperty(PROPERTY_RANGE_UPPERBOUND_COLUMN_VALUE_OUTER, Double.class, 0D);
				Double majorTickIncrement1 = chart.getProperty(PROPERTY_RANGE_MAJORTICKINCREMENT_COLUMN_VALUE_OUTER, Double.class, 10D);
				StandardDialScale standarddialscale1 = new StandardDialScale(lowerBound1, upperBound1, -120D, -300D, majorTickIncrement1, 4);
				standarddialscale1.setTickRadius(0.88D);
				standarddialscale1.setTickLabelOffset(0.14999999999999999D);
				if (chart.getProperty(PROPERTY_RANGE_TICK_LABELS_FONT_OUTER, Font.class) != null) {
					standarddialscale1.setTickLabelFont(chart.getProperty(PROPERTY_RANGE_TICK_LABELS_FONT_OUTER, Font.class));
				}
				standarddialscale1.setTickLabelsVisible(chart.getProperty(PROPERTY_RANGE_TICK_LABELS_VISIBLE_OUTER, Boolean.class, Boolean.TRUE));
				standarddialscale1.setVisible(chart.getProperty(PROPERTY_RANGE_TICK_MARKS_VISIBLE_OUTER, Boolean.class, Boolean.TRUE));
				dialplot.addScale(0, standarddialscale1);

				Double lowerBound2 = chart.getProperty(PROPERTY_RANGE_LOWERBOUND_COLUMN_VALUE_INNER, Double.class, 0D);
				Double upperBound2 = chart.getProperty(PROPERTY_RANGE_UPPERBOUND_COLUMN_VALUE_INNER, Double.class, 0D);
				Double majorTickIncrement2 = chart.getProperty(PROPERTY_RANGE_MAJORTICKINCREMENT_COLUMN_VALUE_INNER, Double.class, 10D);
				StandardDialScale standarddialscale2 = new StandardDialScale(lowerBound2, upperBound2, -120D, -300D, majorTickIncrement2, 4);
				standarddialscale2.setTickRadius(0.5D);
				standarddialscale2.setTickLabelOffset(0.14999999999999999D);
				if (chart.getProperty(PROPERTY_RANGE_TICK_LABELS_FONT_INNER, Font.class) != null) {
					standarddialscale2.setTickLabelFont(chart.getProperty(PROPERTY_RANGE_TICK_LABELS_FONT_INNER, Font.class));
				}
				standarddialscale2.setTickLabelsVisible(chart.getProperty(PROPERTY_RANGE_TICK_LABELS_VISIBLE_INNER, Boolean.class, Boolean.TRUE));
				standarddialscale2.setVisible(chart.getProperty(PROPERTY_RANGE_TICK_MARKS_VISIBLE_INNER, Boolean.class, Boolean.TRUE));
				standarddialscale2.setMajorTickPaint(Color.red);
				standarddialscale2.setMinorTickPaint(Color.red);
				dialplot.addScale(1, standarddialscale2);
				dialplot.mapDatasetToScale(1, 1);
				
				org.jfree.chart.plot.dial.DialPointer.Pin pin = new org.jfree.chart.plot.dial.DialPointer.Pin(1);
				pin.setRadius(0.55000000000000004D);
				dialplot.addPointer(pin);
				org.jfree.chart.plot.dial.DialPointer.Pointer pointer = new org.jfree.chart.plot.dial.DialPointer.Pointer(0);
				dialplot.addPointer(pointer);

				DialCap dialcap = new DialCap();
				dialcap.setRadius(0.10000000000000001D);
				dialplot.setCap(dialcap);

				return jfreechart;
			}
			@Override
			public List<Pair<String, String>> getChartProperties(Chart c, JFreeChart chart) {
				List<Pair<String, String>> result = new ArrayList<Pair<String,String>>();
				
				result.addAll(getCommonChartProperties(chart));

				if (!(chart.getPlot() instanceof DialPlot)) {
					return result;
				}
				
				// set properties for dial plot
				
				// this will be done by the custom editor itself
				
				return result;
			}			
			@Override
			public ChartColumn[] getValueColumnDesc(boolean withScaleColumn) {
				return new ChartColumn[] {
						new ChartColumn(Chart.PROPERTY_VALUE_COLUMN_INNER, Chart.PROPERTY_VALUE_COLUMN_INNER, Number.class),
						new ChartColumn(Chart.PROPERTY_VALUE_COLUMN_OUTER, Chart.PROPERTY_VALUE_COLUMN_OUTER, Number.class)
				};
			}
			@Override
			public ChartColumn[] getDomainColumnDesc() {
				return new ChartColumn[] {
						new ChartColumn(Chart.PROPERTY_RANGE_LOWERBOUND_COLUMN_INNER, Chart.PROPERTY_RANGE_LOWERBOUND_COLUMN_INNER, Number.class),
						new ChartColumn(Chart.PROPERTY_RANGE_UPPERBOUND_COLUMN_INNER, Chart.PROPERTY_RANGE_UPPERBOUND_COLUMN_INNER, Number.class),
						new ChartColumn(Chart.PROPERTY_RANGE_MAJORTICKINCREMENT_COLUMN_INNER, Chart.PROPERTY_RANGE_MAJORTICKINCREMENT_COLUMN_INNER, Number.class),
						new ChartColumn(Chart.PROPERTY_RANGE_LOWERBOUND_COLUMN_OUTER, Chart.PROPERTY_RANGE_LOWERBOUND_COLUMN_OUTER, Number.class),
						new ChartColumn(Chart.PROPERTY_RANGE_UPPERBOUND_COLUMN_OUTER, Chart.PROPERTY_RANGE_UPPERBOUND_COLUMN_OUTER, Number.class),
						new ChartColumn(Chart.PROPERTY_RANGE_MAJORTICKINCREMENT_COLUMN_OUTER, Chart.PROPERTY_RANGE_MAJORTICKINCREMENT_COLUMN_OUTER, Number.class),
				};
			}
			
			class DefaultDialPlotEditor extends CustomChartEditor implements ActionListener {

			    private JTextField label;
			    private JTextField labelInner;
			    private JTextField labelOuter;
			    private Font labelFont;
			    private PaintSample labelPaintSample;
			    private JTextField labelFontField;
			    private Font tickLabelFontInner;
			    private JTextField tickLabelFontFieldInner;
			    private Font tickLabelFontOuter;
			    private JTextField tickLabelFontFieldOuter;
			    private JPanel slot1;
			    private JPanel slot2;
			    private JCheckBox showTickLabelsCheckBoxInner;
			    private JCheckBox showTickMarksCheckBoxInner;
			    private JCheckBox showTickLabelsCheckBoxOuter;
			    private JCheckBox showTickMarksCheckBoxOuter;
			    private JTabbedPane otherTabs;
			    protected ResourceBundle localizationResources
			            = ResourceBundleWrapper.getBundle(
			                    "org.jfree.chart.editor.LocalizationBundle");
			    
			    public DefaultDialPlotEditor(Chart chart) {
			    	setName(localizationResources.getString("Range_Axis"));
			    	
			        this.labelFont = chart.getProperty(Chart.PROPERTY_RANGE_LABEL_FONT, Font.class, new Font("Dialog", 1, 12));
			        this.labelPaintSample = new PaintSample(chart.getProperty(Chart.PROPERTY_RANGE_LABEL_COLOR, Color.class, Color.BLACK));
			        
			        this.tickLabelFontInner = chart.getProperty(Chart.PROPERTY_RANGE_TICK_LABELS_FONT_INNER, Font.class, new Font("Tahoma", 1, 12));
			        this.tickLabelFontOuter = chart.getProperty(Chart.PROPERTY_RANGE_TICK_LABELS_FONT_OUTER, Font.class, new Font("Tahoma", 1, 12));

			        setLayout(new BorderLayout());

			        JPanel general = new JPanel(new BorderLayout());
			        general.setBorder(
			            BorderFactory.createTitledBorder(
			                BorderFactory.createEtchedBorder(),
			                localizationResources.getString("General")
			            )
			        );

			        JPanel interior = new JPanel(new LCBLayout(5));
			        interior.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
			        interior.add(new JLabel(localizationResources.getString("Label")));
			        this.label = new JTextField(chart.getProperty(Chart.PROPERTY_RANGE_LABEL, String.class));
			        interior.add(this.label);
			        interior.add(new JPanel());

			        interior.add(new JLabel(localizationResources.getString("Font")));
			        this.labelFontField = new FontDisplayField(this.labelFont);
			        interior.add(this.labelFontField);
			        JButton b = new JButton(localizationResources.getString("Select..."));
			        b.setActionCommand("SelectLabelFont");
			        b.addActionListener(this);
			        interior.add(b);

			        interior.add(new JLabel(localizationResources.getString("Paint")));
			        interior.add(this.labelPaintSample);
			        b = new JButton(localizationResources.getString("Select..."));
			        b.setActionCommand("SelectLabelPaint");
			        b.addActionListener(this);
			        interior.add(b);

			        general.add(interior);

			        add(general, BorderLayout.NORTH);

			        this.slot1 = new JPanel(new BorderLayout());

			        JPanel other = new JPanel(new BorderLayout());
			        other.setBorder(BorderFactory.createTitledBorder(
			                             BorderFactory.createEtchedBorder(),
			                             localizationResources.getString("Other")));

			        this.otherTabs = new JTabbedPane();
			        this.otherTabs.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

			        JPanel ticksInner = new JPanel(new LCBLayout(5));
			        ticksInner.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

			        JLabel ticksInnerLabel = new JLabel(localizationResources.getString("Label"));
			        ticksInnerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
			        ticksInner.add(ticksInnerLabel);
			        this.labelInner = new JTextField(chart.getProperty(Chart.PROPERTY_RANGE_LABEL_INNER, String.class));
			        ticksInner.add(this.labelInner);
			        ticksInner.add(new JPanel());

			        this.showTickLabelsCheckBoxInner = new JCheckBox(
			            localizationResources.getString("Show_tick_labels"),
			            chart.getProperty(Chart.PROPERTY_RANGE_TICK_LABELS_VISIBLE_INNER, Boolean.class, Boolean.TRUE)
			        );
			        ticksInner.add(this.showTickLabelsCheckBoxInner);
			        ticksInner.add(new JPanel());
			        ticksInner.add(new JPanel());

			        ticksInner.add(
			            new JLabel(localizationResources.getString("Tick_label_font"))
			        );

			        this.tickLabelFontFieldInner = new FontDisplayField(this.tickLabelFontInner);
			        ticksInner.add(this.tickLabelFontFieldInner);
			        b = new JButton(localizationResources.getString("Select..."));
			        b.setActionCommand("SelectTickLabelFontInner");
			        b.addActionListener(this);
			        ticksInner.add(b);

			        this.showTickMarksCheckBoxInner = new JCheckBox(
			            localizationResources.getString("Show_tick_marks"),
			    		chart.getProperty(Chart.PROPERTY_RANGE_TICK_MARKS_VISIBLE_INNER, Boolean.class, Boolean.TRUE)
			        );
			        ticksInner.add(this.showTickMarksCheckBoxInner);
			        ticksInner.add(new JPanel());
			        ticksInner.add(new JPanel());

			        this.otherTabs.add(SpringLocaleDelegate.getInstance().getMessage("Chart.dialChart.properties.ticksInner", "Markierungen (Innen)"), ticksInner);

			        JPanel ticksOuter = new JPanel(new LCBLayout(5));
			        ticksOuter.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

			        JLabel ticksOuterLabel = new JLabel(localizationResources.getString("Label"));
			        ticksOuterLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
			        ticksOuter.add(ticksOuterLabel);
			        this.labelOuter = new JTextField(chart.getProperty(Chart.PROPERTY_RANGE_LABEL_OUTER, String.class));
			        ticksOuter.add(this.labelOuter);
			        ticksOuter.add(new JPanel());

			        this.showTickLabelsCheckBoxOuter = new JCheckBox(
			            localizationResources.getString("Show_tick_labels"),
			            chart.getProperty(Chart.PROPERTY_RANGE_TICK_LABELS_VISIBLE_OUTER, Boolean.class, Boolean.TRUE)
			        );
			        ticksOuter.add(this.showTickLabelsCheckBoxOuter);
			        ticksOuter.add(new JPanel());
			        ticksOuter.add(new JPanel());

			        ticksOuter.add(
			            new JLabel(localizationResources.getString("Tick_label_font"))
			        );

			        this.tickLabelFontFieldOuter = new FontDisplayField(this.tickLabelFontOuter);
			        ticksOuter.add(this.tickLabelFontFieldOuter);
			        b = new JButton(localizationResources.getString("Select..."));
			        b.setActionCommand("SelectTickLabelFontOuter");
			        b.addActionListener(this);
			        ticksOuter.add(b);

			        this.showTickMarksCheckBoxOuter = new JCheckBox(
			            localizationResources.getString("Show_tick_marks"),
			    		chart.getProperty(Chart.PROPERTY_RANGE_TICK_MARKS_VISIBLE_OUTER, Boolean.class, Boolean.TRUE)
			        );
			        ticksOuter.add(this.showTickMarksCheckBoxOuter);
			        ticksOuter.add(new JPanel());
			        ticksOuter.add(new JPanel());

			        this.otherTabs.add(SpringLocaleDelegate.getInstance().getMessage("Chart.dialChart.properties.ticksOuter", "Markierungen (Aussen)"), ticksOuter);

			        other.add(this.otherTabs);

			        this.slot1.add(other);

			        this.slot2 = new JPanel(new BorderLayout());
			        this.slot2.add(this.slot1, BorderLayout.NORTH);
			        add(this.slot2);
			    }
			    public void actionPerformed(ActionEvent event) {
			        String command = event.getActionCommand();
			        if (command.equals("SelectLabelFont")) {
			            attemptLabelFontSelection();
			        }
			        else if (command.equals("SelectLabelPaint")) {
			            attemptModifyLabelPaint();
			        }
			        else if (command.equals("SelectTickLabelFontInner")) {
			            attemptTickLabelFontSelectionInner();
			        }
			        else if (command.equals("SelectTickLabelFontOuter")) {
			            attemptTickLabelFontSelectionOuter();
			        }
			    }
			    private void attemptLabelFontSelection() {
			        FontChooserPanel panel = new FontChooserPanel(this.labelFont);
			        int result = JOptionPane.showConfirmDialog(this, panel,
			            localizationResources.getString("Font_Selection"),
			            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

			        if (result == JOptionPane.OK_OPTION) {
			            this.labelFont = panel.getSelectedFont();
			            this.labelFontField.setText(
			                this.labelFont.getFontName() + " " + this.labelFont.getSize()
			            );
			        }
			    }
			    private void attemptModifyLabelPaint() {
			        Color c;
			        c = JColorChooser.showDialog(
			            this, localizationResources.getString("Label_Color"), Color.blue
			        );
			        if (c != null) {
			            this.labelPaintSample.setPaint(c);
			        }
			    }
			    public void attemptTickLabelFontSelectionInner() {
			        FontChooserPanel panel = new FontChooserPanel(this.tickLabelFontInner);
			        int result = JOptionPane.showConfirmDialog(this, panel,
			            localizationResources.getString("Font_Selection"),
			            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

			        if (result == JOptionPane.OK_OPTION) {
			            this.tickLabelFontInner = panel.getSelectedFont();
			            this.tickLabelFontFieldInner.setText(
			                this.tickLabelFontInner.getFontName() + " "
			                + this.tickLabelFontInner.getSize()
			            );
			        }
			    }
			    public void attemptTickLabelFontSelectionOuter() {
			        FontChooserPanel panel = new FontChooserPanel(this.tickLabelFontOuter);
			        int result = JOptionPane.showConfirmDialog(this, panel,
			            localizationResources.getString("Font_Selection"),
			            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

			        if (result == JOptionPane.OK_OPTION) {
			            this.tickLabelFontOuter = panel.getSelectedFont();
			            this.tickLabelFontFieldOuter.setText(
			                this.tickLabelFontOuter.getFontName() + " "
			                + this.tickLabelFontOuter.getSize()
			            );
			        }
			    }
				@Override
			    public List<Pair<String, String>> applyChartProperties(JFreeChart chart) {
					List<Pair<String, String>> result = new ArrayList<Pair<String,String>>();

					result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL, "" + this.label.getText()));
					result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL_FONT, "" + Chart.fontToString(this.labelFont)));
					result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL_COLOR, "" + ((Color)this.labelPaintSample.getPaint()).getRGB()));

					result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL_INNER, "" + this.labelInner.getText()));
					result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_LABEL_OUTER, "" + this.labelOuter.getText()));

					result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_LABELS_FONT_INNER, "" + Chart.fontToString(this.tickLabelFontInner)));
					result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_LABELS_VISIBLE_INNER, "" + this.showTickLabelsCheckBoxInner.isSelected()));
					result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_MARKS_VISIBLE_INNER, "" + this.showTickMarksCheckBoxInner.isSelected()));
					result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_LABELS_FONT_OUTER, "" + Chart.fontToString(this.tickLabelFontOuter)));
					result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_LABELS_VISIBLE_OUTER, "" + this.showTickLabelsCheckBoxOuter.isSelected()));
					result.add(new Pair<String, String>(Chart.PROPERTY_RANGE_TICK_MARKS_VISIBLE_OUTER, "" + this.showTickMarksCheckBoxOuter.isSelected()));
					
					return result;
			    }
			}
			@Override
			public JTabbedPanel[] getCustomPlotEditorPanels(Chart chart, String prefix) {
				CustomChartEditor editor = new DefaultDialPlotEditor(chart);
				return new JTabbedPanel[] {
						new JTabbedPanel(editor.getName(), editor)
				};
			}
		},
		COMBINEDCATEGORYCHART {
			@Override
			protected Dataset[] createDataset(Chart chart) {
				List<Dataset> datasets = new ArrayList<Dataset>();
				
				String combinedPrefixes = chart.getProperty(PROPERTY_COMBINED_PREFIXES, String.class);
				String[] prefixes = combinedPrefixes.split(":");
				for (int i = 0; i < prefixes.length; i++) {
					String prefix = prefixes[i];
					if (!StringUtils.isNullOrEmpty(prefix)) {
						ChartFunction cFunction = ChartFunction.valueOf(prefix.split("\\.")[0]);
						datasets.add(cFunction.createDataset(chart, prefix + ":")[0]);
					}
				}
				
				return datasets.toArray(new Dataset[0]);
	        }
			@Override
			public JFreeChart createChart(final Chart chart) {
				Dataset[] datasets = createDataset(chart);
				
				final DrawingSupplier drawingSupplier = new DefaultDrawingSupplier(); 
				final CategoryPlot categoryPlot = new CategoryPlot() {
					public org.jfree.chart.plot.DrawingSupplier getDrawingSupplier() {
						return drawingSupplier;
					};
				};
				JFreeChart jfreechart = new JFreeChart(categoryPlot);
				jfreechart.setTitle("");

				ChartUtilities.applyCurrentTheme(jfreechart);
				
				if (!(jfreechart.getPlot() instanceof CategoryPlot)) {
					return jfreechart;
				}

				// set properties for category plot
				((CategoryPlot)jfreechart.getPlot()).setOrientation(
						chart.getProperty(PROPERTY_PLOT_ORIENTATION, String.class, PlotOrientation.HORIZONTAL.toString())
							.equals(PlotOrientation.VERTICAL.toString()) ? PlotOrientation.VERTICAL : PlotOrientation.HORIZONTAL);

				String combinedPrefixes = chart.getProperty(PROPERTY_COMBINED_PREFIXES, String.class);
				String[] prefixes = combinedPrefixes.split(":");
				for (int i = 0; i < prefixes.length; i++) {
					final String prefix = prefixes[i];
					if (StringUtils.isNullOrEmpty(prefix)) {
						continue;
					}
					ChartFunction cFunction = ChartFunction.valueOf(prefix.split("\\.")[0]);
					CategoryItemRenderer renderer = null;
					if (cFunction == BARCHART) {
						renderer = new BarRenderer() {
							private Map<Integer, Double> barWidths = new HashMap<Integer, Double>();
							@Override
							public void setSeriesStroke(int series, Stroke stroke) {
								if (stroke instanceof BasicStroke) {
									barWidths.put(Integer.valueOf(series), new Double(((BasicStroke)stroke).getLineWidth()));
								}
							};
							@Override
							public void drawItem(Graphics2D g2, CategoryItemRendererState state, final Rectangle2D dataArea, 
									final CategoryPlot plot, CategoryAxis domainAxis, final ValueAxis rangeAxis, CategoryDataset dataset, int row, int column, int pass) {
								final Double barWidth = state.getBarWidth();
								if (barWidths.containsKey(Integer.valueOf(row))) {
									state.setBarWidth(barWidths.get(Integer.valueOf(row)));
								}
								super.drawItem(g2, state, dataArea, plot, domainAxis, rangeAxis, dataset, row, column, pass);
								
								state.setBarWidth(barWidth);
								
								setBarPainter(new GradientBarPainter() {
									@Override
								    public void paintBar(Graphics2D g2, BarRenderer renderer, int row, int column, RectangularShape bar, RectangleEdge base) {
								        Paint itemPaint = renderer.getItemPaint(row, column);

								        Color c0, c1;
								        if (itemPaint instanceof Color) {
								            c0 = (Color) itemPaint;
								            c1 = c0.brighter();
								        } else if (itemPaint instanceof GradientPaint) {
								            GradientPaint gp = (GradientPaint) itemPaint;
								            c0 = gp.getColor1();
								            c1 = gp.getColor2();
								        } else {
								            c0 = Color.blue;
								            c1 = Color.blue.brighter();
								        }

								        // as a special case, if the bar colour has alpha == 0, we draw
								        // nothing.
								        if (c0.getAlpha() == 0) {
								            return;
								        }

								        if (base == RectangleEdge.TOP || base == RectangleEdge.BOTTOM) {
								        	double g1x = 0.10;
									        double g2x = 0.20;
									        double g3x = 0.80;
								            Rectangle2D[] regions = splitVerticalBar(bar, g1x, g2x,
								                    g3x);
								            GradientPaint gp = new GradientPaint((float) regions[0].getMinX(),
								                    0.0f, c0, (float) regions[0].getMaxX(), 0.0f, Color.white);
								            g2.setPaint(gp);
								            g2.fill(regions[0]);

								            gp = new GradientPaint((float) regions[1].getMinX(), 0.0f,
								                    Color.white, (float) regions[1].getMaxX(), 0.0f, c0);
								            g2.setPaint(gp);
								            g2.fill(regions[1]);

								            gp = new GradientPaint((float) regions[2].getMinX(), 0.0f, c0,
								                    (float) regions[2].getMaxX(), 0.0f, c1);
								            g2.setPaint(gp);
								            g2.fill(regions[2]);

								            gp = new GradientPaint((float) regions[3].getMinX(), 0.0f, c1,
								                     (float) regions[3].getMaxX(), 0.0f, c0);
								            g2.setPaint(gp);
								            g2.fill(regions[3]);
								        } else if (base == RectangleEdge.LEFT || base == RectangleEdge.RIGHT) {
								        	double g1x = 0.10;
									        double g2x = 0.20;
									        double g3x = 0.80;
								            Rectangle2D[] regions = splitHorizontalBar(bar, g1x, g2x,
								                    g3x);
								            GradientPaint gp = new GradientPaint(0.0f,
								                    (float) regions[0].getMinY(), c0, 0.0f,
								                    (float) regions[0].getMaxX(), Color.white);
								            g2.setPaint(gp);
								            g2.fill(regions[0]);

								            gp = new GradientPaint(0.0f, (float) regions[1].getMinY(),
								                    Color.white, 0.0f, (float) regions[1].getMaxY(), c0);
								            g2.setPaint(gp);
								            g2.fill(regions[1]);

								            gp = new GradientPaint(0.0f, (float) regions[2].getMinY(), c0,
								                    0.0f, (float) regions[2].getMaxY(), c1);
								            g2.setPaint(gp);
								            g2.fill(regions[2]);

								            gp = new GradientPaint(0.0f, (float) regions[3].getMinY(), c1,
								                     0.0f, (float) regions[3].getMaxY(), c0);
								            g2.setPaint(gp);
								            g2.fill(regions[3]);
								        }
								        
								        String cStandardRange = chart.getProperty(prefix + ":" + PROPERTY_BARCHART_STANDARDRANGE_COLUMN_VALUE + ":" + row, String.class);
										if (cStandardRange != null && cStandardRange.indexOf(";") != -1) {
											final RectangleEdge edge = plot.getRangeAxisEdge();
											final String[] args = cStandardRange.split(";");
											for (String arg : args) {
												try {
													final double val = Double.parseDouble(arg);
													final double value = rangeAxis.valueToJava2D(val, dataArea, edge);

													final Font of = g2.getFont();
													final Paint op = g2.getPaint();
													final Stroke os = g2.getStroke();

													final RectangularShape rs;
													if (plot.getOrientation() == PlotOrientation.VERTICAL) {
														rs = new Rectangle2D.Double(bar.getMinX(), value-10, bar.getWidth()+15, 1);
													} else {
														rs = new Rectangle2D.Double(value, bar.getMinY()-(bar.getMaxY()-bar.getMinY()), 1, (bar.getMaxY()-bar.getMinY())+10);
													}
													g2.setStroke(new BasicStroke());
											        g2.setPaint(Color.LIGHT_GRAY);
											        g2.draw(rs);
											        
											        g2.setPaint(Color.DARK_GRAY);
											        g2.setFont(new Font("Dialog", 0, 11));
											        if (plot.getOrientation() == PlotOrientation.VERTICAL) {
											        	g2.drawString(NumberFormat.getInstance().format(val), (float)(bar.getMinX()+rs.getWidth()-8), (float)(value-12));
													} else {
														g2.drawString(NumberFormat.getInstance().format(val), (float)(rs.getX()+rs.getWidth()+2), (float)((rs.getY()+rs.getHeight())-(bar.getMaxY()-bar.getMinY())));
													}

											        g2.setStroke(os);
											        g2.setPaint(op);
											        g2.setFont(of);
												} catch (Exception e) {
													// ignore.
												}
											}
										}
																        
								        // draw the outline...
								        if (renderer.isDrawBarOutline()
								            /*&& state.getBarWidth() > renderer.BAR_OUTLINE_WIDTH_THRESHOLD*/) {
								            Stroke stroke = renderer.getItemOutlineStroke(row, column);
								            Paint paint = renderer.getItemOutlinePaint(row, column);
								            if (stroke != null && paint != null) {
								                g2.setStroke(stroke);
								                g2.setPaint(paint);
								                g2.draw(bar);
								            }
								        }
								    }	
									
								    private Rectangle2D[] splitVerticalBar(RectangularShape bar, double a, double b, double c) {
								        Rectangle2D[] result = new Rectangle2D[4];
								        double x0 = bar.getMinX() - 1;
								        double x1 = Math.rint(x0 + (bar.getWidth() * a));
								        double x2 = Math.rint(x0 + (bar.getWidth() * b));
								        double x3 = Math.rint(x0 + (bar.getWidth() * c));
								        result[0] = new Rectangle2D.Double(bar.getMinX(), bar.getMinY(),
								                x1 - x0, bar.getHeight());
								        result[1] = new Rectangle2D.Double(x1, bar.getMinY(), x2 - x1,
								                bar.getHeight());
								        result[2] = new Rectangle2D.Double(x2 - 1, bar.getMinY(), x3 - x2 + 2,
								                bar.getHeight());
								        result[3] = new Rectangle2D.Double(x3, bar.getMinY(),
								                bar.getMaxX() - x3, bar.getHeight());
								        return result;
								    }
							        private Rectangle2D[] splitHorizontalBar(RectangularShape bar, double a, double b, double c) {
							            Rectangle2D[] result = new Rectangle2D[4];
							            double y0 = bar.getMinY();
							            double y1 = Math.rint(y0 + (bar.getHeight() * a));
							            double y2 = Math.rint(y0 + (bar.getHeight() * b));
							            double y3 = Math.rint(y0 + (bar.getHeight() * c));
							            result[0] = new Rectangle2D.Double(bar.getMinX(), bar.getMinY(),
							                    bar.getWidth(), y1 - y0);
							            result[1] = new Rectangle2D.Double(bar.getMinX(), y1, bar.getWidth(),
							                    y2 - y1);
							            result[2] = new Rectangle2D.Double(bar.getMinX(), y2, bar.getWidth(),
							                    y3 - y2);
							            result[3] = new Rectangle2D.Double(bar.getMinX(), y3, bar.getWidth(),
							                    bar.getMaxY() - y3);
							            return result;
							        }
								});
							}
						};
					} else if (cFunction == LINECHART) {
						renderer = new LineAndShapeRenderer(true, false) {
							@Override
							public Stroke getItemStroke(int row, int column) {
								if (column == 0)
									return new BasicStroke();
								return super.getItemStroke(row, column);
							}
						};
					}
					
					categoryPlot.setRenderer(i, renderer);
					categoryPlot.setDomainAxis(i, new CategoryAxis());
					categoryPlot.setRangeAxis(i, new NumberAxis());
					categoryPlot.mapDatasetToRangeAxis(i, i);
					categoryPlot.mapDatasetToDomainAxis(i, i);
					categoryPlot.setDataset(i, (CategoryDataset)datasets[i]);
					
					// set properties for domain axis
					((CategoryPlot)jfreechart.getPlot()).getDomainAxis(i).setLabel(chart.getProperty(prefix + PROPERTY_DOMAIN_LABEL, String.class));
					if (chart.getProperty(prefix + PROPERTY_DOMAIN_LABEL_FONT, Font.class) != null) {
						((CategoryPlot)jfreechart.getPlot()).getDomainAxis(i).setLabelFont(chart.getProperty(prefix + PROPERTY_DOMAIN_LABEL_FONT, Font.class));
					}
					((CategoryPlot)jfreechart.getPlot()).getDomainAxis(i).setLabelPaint(chart.getProperty(prefix + PROPERTY_DOMAIN_LABEL_COLOR, Color.class, Color.BLACK));
					if (chart.getProperty(prefix + PROPERTY_DOMAIN_TICK_LABELS_FONT, Font.class) != null) {
						((CategoryPlot)jfreechart.getPlot()).getDomainAxis(i).setTickLabelFont(chart.getProperty(prefix + PROPERTY_DOMAIN_TICK_LABELS_FONT, Font.class));
					}
					((CategoryPlot)jfreechart.getPlot()).getDomainAxis(i).setTickLabelsVisible(chart.getProperty(prefix + PROPERTY_DOMAIN_TICK_LABELS_VISIBLE, Boolean.class, Boolean.TRUE));
					((CategoryPlot)jfreechart.getPlot()).getDomainAxis(i).setTickMarksVisible(chart.getProperty(prefix + PROPERTY_DOMAIN_TICK_MARKS_VISIBLE, Boolean.class, Boolean.TRUE));
					// set properties for range axis
					((CategoryPlot)jfreechart.getPlot()).getRangeAxis(i).setLabel(chart.getProperty(prefix + PROPERTY_RANGE_LABEL, String.class));
					if (chart.getProperty(prefix + PROPERTY_RANGE_LABEL_FONT, Font.class) != null) {
						((CategoryPlot)jfreechart.getPlot()).getRangeAxis(i).setLabelFont(chart.getProperty(prefix + PROPERTY_RANGE_LABEL_FONT, Font.class));
					}
					((CategoryPlot)jfreechart.getPlot()).getRangeAxis(i).setLabelPaint(chart.getProperty(prefix + PROPERTY_RANGE_LABEL_COLOR, Color.class, Color.BLACK));
					if (chart.getProperty(prefix + PROPERTY_RANGE_TICK_LABELS_FONT, Font.class) != null) {
						((CategoryPlot)jfreechart.getPlot()).getRangeAxis(i).setTickLabelFont(chart.getProperty(prefix + PROPERTY_RANGE_TICK_LABELS_FONT, Font.class));
					}
					((CategoryPlot)jfreechart.getPlot()).getRangeAxis(i).setTickLabelsVisible(chart.getProperty(prefix + PROPERTY_RANGE_TICK_LABELS_VISIBLE, Boolean.class, Boolean.TRUE));
					((CategoryPlot)jfreechart.getPlot()).getRangeAxis(i).setTickMarksVisible(chart.getProperty(prefix + PROPERTY_RANGE_TICK_MARKS_VISIBLE, Boolean.class, Boolean.TRUE));
					((CategoryPlot)jfreechart.getPlot()).getRangeAxis(i).setAutoRange(chart.getProperty(prefix + PROPERTY_RANGE_AUTORANGE, Boolean.class, Boolean.TRUE));
					if (!((CategoryPlot)jfreechart.getPlot()).getRangeAxis(i).isAutoRange()) {
						((CategoryPlot)jfreechart.getPlot()).getRangeAxis(i).setLowerBound(chart.getProperty(prefix + PROPERTY_RANGE_LOWERBOUND, Double.class, 0D));
						((CategoryPlot)jfreechart.getPlot()).getRangeAxis(i).setUpperBound(chart.getProperty(prefix + PROPERTY_RANGE_UPPERBOUND, Double.class, 0D));
					}
					
					// set width and colors
					for (int j = 0; j < ((CategoryPlot)jfreechart.getPlot()).getDataset(i).getRowKeys().size(); j++) {
						Object cItemWidth = chart.getProperty(prefix + ":" + PROPERTY_ITEMWIDTH_COLUMN_VALUE + ":" + j, Number.class);
						if (cItemWidth instanceof Number) {
							if (((Number)cItemWidth).floatValue() > 0) {
								((AbstractRenderer)((CategoryPlot)jfreechart.getPlot()).getRenderer(i)).setSeriesStroke(j,
										new BasicStroke(((Number)cItemWidth).floatValue()));
							}
						}
						Color cItemColor = chart.getProperty(prefix + ":" + PROPERTY_ITEMCOLOR_COLUMN_VALUE + ":" + j, Color.class);
						if (cItemColor != null) {
							((AbstractRenderer)((CategoryPlot)jfreechart.getPlot()).getRenderer(i)).setSeriesPaint(j, cItemColor);
						}
					}
					
					// set markers.
					Comparable cDomainMarker = chart.getProperty(prefix + ":" + PROPERTY_DOMAIN_MARKER_COLUMN_VALUE, String.class);
					if (cDomainMarker instanceof Comparable) {
						CategoryMarker marker1 = new CategoryMarker(cDomainMarker, Color.LIGHT_GRAY, new BasicStroke(1.0F));
						marker1.setDrawAsLine(true);
						marker1.setLabel(chart.getProperty(PROPERTY_DOMAIN_MARKER_LABEL, String.class));
						marker1.setLabelFont(new Font("Dialog", 0, 11));
						marker1.setLabelPaint(Color.DARK_GRAY);
						if (((CategoryPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.VERTICAL) {
							marker1.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
						} else {
							marker1.setLabelAnchor(RectangleAnchor.RIGHT);
							marker1.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
						}
						marker1.setLabelOffset(new RectangleInsets(2D, 5D, 2D, 5D));
						((CategoryPlot)jfreechart.getPlot()).addDomainMarker(marker1, Layer.BACKGROUND);
						CategoryMarker marker2 = new CategoryMarker(cDomainMarker, Color.LIGHT_GRAY, new BasicStroke(1.0F));
						marker2.setDrawAsLine(true);
						marker2.setLabel("" + marker2.getKey());
						marker2.setLabelFont(new Font("Dialog", 0, 11));
						marker2.setLabelPaint(Color.DARK_GRAY);
						if (((CategoryPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.VERTICAL) {
							marker2.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
							marker2.setLabelOffset(new RectangleInsets(15D, 5D, 2D, 5D));
						} else {
							marker2.setLabelAnchor(RectangleAnchor.RIGHT);
							marker2.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
							marker2.setLabelOffset(new RectangleInsets(-25D, 5D, 2D, 5D));
						}
						((CategoryPlot)jfreechart.getPlot()).addDomainMarker(marker2, Layer.BACKGROUND);
					}
					Object cRangeMarker = chart.getProperty(prefix + ":" + PROPERTY_RANGE_MARKER_COLUMN_VALUE, Number.class);
					if (cRangeMarker != null && cRangeMarker instanceof Number) {
						ValueMarker marker1 = new ValueMarker(((Number)cRangeMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
						marker1.setLabel(chart.getProperty(prefix + PROPERTY_RANGE_MARKER_LABEL, String.class));
						marker1.setLabelFont(new Font("Dialog", 0, 11));
						marker1.setLabelPaint(Color.DARK_GRAY);
						if (((CategoryPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.HORIZONTAL) {
							marker1.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
						} else {
							marker1.setLabelAnchor(RectangleAnchor.RIGHT);
							marker1.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
						}
						marker1.setLabelOffset(new RectangleInsets(2D, 5D, 2D, 5D));
						((CategoryPlot)jfreechart.getPlot()).addRangeMarker(marker1, Layer.BACKGROUND);
						ValueMarker marker2 = new ValueMarker(((Number)cRangeMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
						marker2.setLabel("" + CollectableFieldFormat.getInstance(Double.class).format("#,##0.00", marker2.getValue()));
						marker2.setLabelFont(new Font("Dialog", 0, 11));
						marker2.setLabelPaint(Color.DARK_GRAY);	
						if (((CategoryPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.HORIZONTAL) {
							marker2.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
							marker2.setLabelOffset(new RectangleInsets(15D, 5D, 2D, 5D));
						} else {
							marker2.setLabelAnchor(RectangleAnchor.RIGHT);
							marker2.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
							marker2.setLabelOffset(new RectangleInsets(-25D, 5D, 2D, 5D));
						}
						((CategoryPlot)jfreechart.getPlot()).addRangeMarker(marker2, Layer.BACKGROUND);
					}
					
					if (chart.getProperty(prefix + PROPERTY_MOUSE_MARKER_SELECTED, Boolean.class, Boolean.FALSE)) {
						renderer.setBaseToolTipGenerator(
								new StandardCategoryToolTipGenerator("{2}", SpringLocaleDelegate.getInstance().getNumberFormat()));
					}
					if (chart.getProperty(prefix + PROPERTY_SHOW_MARKER_SELECTED, Boolean.class, Boolean.FALSE)) {
						renderer.setBaseItemLabelGenerator(
								new StandardCategoryItemLabelGenerator("{2}", SpringLocaleDelegate.getInstance().getNumberFormat()));
						renderer.setBaseItemLabelsVisible(true);
					}

				}
				
				categoryPlot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
			    
				setCommonChartProperties(chart, jfreechart);

				/*XStream xstream = new XStream();
				xstream.autodetectAnnotations(true);
				String xml = xstream.toXML(jfreechart);
				chart.gets
				xml = xml.replaceAll("XXX", "YYY");
				System.err.println(xml);
				jfreechart = (JFreeChart)xstream.fromXML(xml);*/
				return jfreechart;
			}
			@Override
			public List<Pair<String, String>> getChartProperties(Chart c, JFreeChart chart) {
				List<Pair<String, String>> result = new ArrayList<Pair<String,String>>();
				
				result.addAll(getCommonChartProperties(chart));

				if (!(chart.getPlot() instanceof CategoryPlot)) {
					return result;
				}
				
				// set properties for category plot
				result.add(new Pair<String, String>(Chart.PROPERTY_PLOT_ORIENTATION, "" + ((CategoryPlot)chart.getPlot()).getOrientation().toString()));

				String combinedPrefixes = c.getProperty(PROPERTY_COMBINED_PREFIXES, String.class);
				String[] prefixes = combinedPrefixes.split(":");
				for (int i = 0; i < prefixes.length; i++) {
					String prefix = prefixes[i];
					if (StringUtils.isNullOrEmpty(prefix)) {
						continue;
					}
					
					// set properties for domain axis
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_DOMAIN_LABEL, "" + ((CategoryPlot)chart.getPlot()).getDomainAxis(i).getLabel()));
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_DOMAIN_LABEL_FONT, "" + fontToString(((CategoryPlot)chart.getPlot()).getDomainAxis(i).getLabelFont())));
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_DOMAIN_LABEL_COLOR, "" + ((Color)((CategoryPlot)chart.getPlot()).getDomainAxis(i).getLabelPaint()).getRGB()));
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_DOMAIN_TICK_LABELS_FONT, "" + fontToString(((CategoryPlot)chart.getPlot()).getDomainAxis(i).getTickLabelFont())));
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_DOMAIN_TICK_LABELS_VISIBLE, "" + ((CategoryPlot)chart.getPlot()).getDomainAxis(i).isTickLabelsVisible()));
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_DOMAIN_TICK_MARKS_VISIBLE, "" + ((CategoryPlot)chart.getPlot()).getDomainAxis(i).isTickMarksVisible()));
					// set properties for range axis
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_RANGE_LABEL, "" + ((CategoryPlot)chart.getPlot()).getRangeAxis(i).getLabel()));
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_RANGE_LABEL_FONT, "" + fontToString(((CategoryPlot)chart.getPlot()).getRangeAxis(i).getLabelFont())));
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_RANGE_LABEL_COLOR, "" + ((Color)((CategoryPlot)chart.getPlot()).getRangeAxis(i).getLabelPaint()).getRGB()));
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_RANGE_TICK_LABELS_FONT, "" + fontToString(((CategoryPlot)chart.getPlot()).getRangeAxis(i).getTickLabelFont())));
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_RANGE_TICK_LABELS_VISIBLE, "" + ((CategoryPlot)chart.getPlot()).getRangeAxis(i).isTickLabelsVisible()));
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_RANGE_TICK_MARKS_VISIBLE, "" + ((CategoryPlot)chart.getPlot()).getRangeAxis(i).isTickMarksVisible()));
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_RANGE_AUTORANGE, "" + ((CategoryPlot)chart.getPlot()).getRangeAxis(i).isAutoRange()));
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_RANGE_LOWERBOUND, "" + ((CategoryPlot)chart.getPlot()).getRangeAxis(i).getLowerBound()));
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_RANGE_UPPERBOUND, "" + ((CategoryPlot)chart.getPlot()).getRangeAxis(i).getUpperBound()));
				}
				
				return result;
			}			
			@Override
			public ChartColumn[] getValueColumnDesc(boolean withScaleColumn) {
				return new ChartColumn[] {};
			}
			@Override
			public ChartColumn[] getDomainColumnDesc() {
				return new ChartColumn[] {};
			}
			public boolean isCombinedChart() {
				return true;
			}
			public ChartFunction[] getCombinedChartFunctions() {
				return new ChartFunction[] {BARCHART, LINECHART};
			}
			@Override
			public JTabbedPanel[] getCustomPlotEditorPanels(Chart chart, String prefix) {
				CustomChartEditor editor = new DefaultCombinedPlotEditor(chart);
				CustomChartEditor editorAppearance = new DefaultCombinedPlotAppearanceEditor(chart);
				return new JTabbedPanel[] {
						new JTabbedPanel(editor.getName(), editor),
						new JTabbedPanel(editorAppearance.getName(), editorAppearance)
				};
			}
		},
		COMBINEDXYCHART {
			@Override
			protected Dataset[] createDataset(Chart chart) {
				List<Dataset> datasets = new ArrayList<Dataset>();
				
				String combinedPrefixes = chart.getProperty(PROPERTY_COMBINED_PREFIXES, String.class);
				String[] prefixes = combinedPrefixes.split(":");
				for (int i = 0; i < prefixes.length; i++) {
					String prefix = prefixes[i];
					if (!StringUtils.isNullOrEmpty(prefix)) {
						ChartFunction cFunction = ChartFunction.valueOf(prefix.split("\\.")[0]);
						datasets.add(cFunction.createDataset(chart, prefix + ":")[0]);
					}
				}
				
				return datasets.toArray(new Dataset[0]);
	        }
			@Override
			public JFreeChart createChart(Chart chart) {
				Dataset[] datasets = createDataset(chart);
				
				final DrawingSupplier drawingSupplier = new DefaultDrawingSupplier(); 
				final XYPlot xyPlot = new XYPlot() {
					public org.jfree.chart.plot.DrawingSupplier getDrawingSupplier() {
						return drawingSupplier;
					};
				};
				
				JFreeChart jfreechart = new JFreeChart(xyPlot);
				jfreechart.setTitle("");

				ChartUtilities.applyCurrentTheme(jfreechart);

				if (!(jfreechart.getPlot() instanceof XYPlot)) {
					return jfreechart;
				}
				
				// set properties for xy plot
				((XYPlot)jfreechart.getPlot()).setOrientation(
						chart.getProperty(PROPERTY_PLOT_ORIENTATION, String.class, PlotOrientation.HORIZONTAL.toString())
							.equals(PlotOrientation.VERTICAL.toString()) ? PlotOrientation.VERTICAL : PlotOrientation.HORIZONTAL);

				String combinedPrefixes = chart.getProperty(PROPERTY_COMBINED_PREFIXES, String.class);
				String[] prefixes = combinedPrefixes.split(":");
				for (int i = 0; i < prefixes.length; i++) {
					String prefix = prefixes[i];
					if (StringUtils.isNullOrEmpty(prefix)) {
						continue;
					}
					ChartFunction cFunction = ChartFunction.valueOf(prefix.split("\\.")[0]);
					
					XYItemRenderer renderer = null;
					if (cFunction == XYBARCHART) {
						renderer = new XYBarRenderer();
						((XYBarRenderer)renderer).setShadowVisible(false);
						((XYBarRenderer)renderer).setShadowXOffset(0d);
						((XYBarRenderer)renderer).setShadowYOffset(0d);
					} else if (cFunction == XYSCATTERCHART) {
						renderer = new XYLineAndShapeRenderer(false, true) {
							@Override
							public LegendItem getLegendItem(int datasetIndex, int series) {
						        XYPlot plot = getPlot();
						        if (plot == null) {
						            return null;
						        }
						        LegendItem result = null;
						        XYDataset dataset = plot.getDataset(datasetIndex);
						        if (dataset != null) {
						            if (getItemVisible(series, 0)) {
						                String label = getLegendItemLabelGenerator().generateLabel(
						                        dataset, series);
						                String description = label;
						                String toolTipText = null;
						                if (getLegendItemToolTipGenerator() != null) {
						                    toolTipText = getLegendItemToolTipGenerator().generateLabel(
						                            dataset, series);
						                }
						                String urlText = null;
						                if (getLegendItemURLGenerator() != null) {
						                    urlText = getLegendItemURLGenerator().generateLabel(
						                            dataset, series);
						                }
						                boolean shapeIsVisible = getItemShapeVisible(series, 0);
						                Shape shape = lookupLegendShape(series);
						                boolean shapeIsFilled = getItemShapeFilled(series, 0);
						                Paint fillPaint = (getUseFillPaint()
						                    ? lookupSeriesFillPaint(series)
						                    : lookupSeriesPaint(series));
						                boolean shapeOutlineVisible = getDrawOutlines();
						                Paint outlinePaint = (getUseOutlinePaint()
						                    ? lookupSeriesOutlinePaint(series)
						                    : lookupSeriesPaint(series));
						                Stroke outlineStroke = lookupSeriesOutlineStroke(series);
						                boolean lineVisible = getItemLineVisible(series, 0);
						                Stroke lineStroke = new BasicStroke();
						                Paint linePaint = lookupSeriesPaint(series);
						                result = new LegendItem(label, description, toolTipText,
						                        urlText, shapeIsVisible, shape, shapeIsFilled,
						                        fillPaint, shapeOutlineVisible, outlinePaint,
						                        outlineStroke, lineVisible, getLegendLine(),
						                        lineStroke, linePaint);
						                result.setLabelFont(lookupLegendTextFont(series));
						                Paint labelPaint = lookupLegendTextPaint(series);
						                if (labelPaint != null) {
						                    result.setLabelPaint(labelPaint);
						                }
						                result.setSeriesKey(dataset.getSeriesKey(series));
						                result.setSeriesIndex(series);
						                result.setDataset(dataset);
						                result.setDatasetIndex(datasetIndex);
						            }
						        }
						        return result;
						    }
						};
					} else {
						renderer = new XYLineAndShapeRenderer(true, false) {
							@Override
							public LegendItem getLegendItem(int datasetIndex, int series) {
						        XYPlot plot = getPlot();
						        if (plot == null) {
						            return null;
						        }
						        LegendItem result = null;
						        XYDataset dataset = plot.getDataset(datasetIndex);
						        if (dataset != null) {
						            if (getItemVisible(series, 0)) {
						                String label = getLegendItemLabelGenerator().generateLabel(
						                        dataset, series);
						                String description = label;
						                String toolTipText = null;
						                if (getLegendItemToolTipGenerator() != null) {
						                    toolTipText = getLegendItemToolTipGenerator().generateLabel(
						                            dataset, series);
						                }
						                String urlText = null;
						                if (getLegendItemURLGenerator() != null) {
						                    urlText = getLegendItemURLGenerator().generateLabel(
						                            dataset, series);
						                }
						                boolean shapeIsVisible = getItemShapeVisible(series, 0);
						                Shape shape = lookupLegendShape(series);
						                boolean shapeIsFilled = getItemShapeFilled(series, 0);
						                Paint fillPaint = (getUseFillPaint()
						                    ? lookupSeriesFillPaint(series)
						                    : lookupSeriesPaint(series));
						                boolean shapeOutlineVisible = getDrawOutlines();
						                Paint outlinePaint = (getUseOutlinePaint()
						                    ? lookupSeriesOutlinePaint(series)
						                    : lookupSeriesPaint(series));
						                Stroke outlineStroke = lookupSeriesOutlineStroke(series);
						                boolean lineVisible = getItemLineVisible(series, 0);
						                Stroke lineStroke = new BasicStroke();
						                Paint linePaint = lookupSeriesPaint(series);
						                result = new LegendItem(label, description, toolTipText,
						                        urlText, shapeIsVisible, shape, shapeIsFilled,
						                        fillPaint, shapeOutlineVisible, outlinePaint,
						                        outlineStroke, lineVisible, getLegendLine(),
						                        lineStroke, linePaint);
						                result.setLabelFont(lookupLegendTextFont(series));
						                Paint labelPaint = lookupLegendTextPaint(series);
						                if (labelPaint != null) {
						                    result.setLabelPaint(labelPaint);
						                }
						                result.setSeriesKey(dataset.getSeriesKey(series));
						                result.setSeriesIndex(series);
						                result.setDataset(dataset);
						                result.setDatasetIndex(datasetIndex);
						            }
						        }
						        return result;
						    }
						};
					}

					xyPlot.setRenderer(i, renderer);
					
					final String legendLabel = chart.getProperty(prefix + PROPERTY_LEGEND_LABEL, String.class, "");
					renderer.setLegendItemLabelGenerator(
				    	      new StandardXYSeriesLabelGenerator() {
				    	    	  @Override
				    	    	  public String generateLabel(XYDataset dataset, int series) {
				    	    		  return legendLabel;
				    	    	  }
				    	        }
				    	    );
					
					if (cFunction == TIMESERIESCHART) {
						xyPlot.setDomainAxis(i, new DateAxis());
					} else {
						xyPlot.setDomainAxis(i, new NumberAxis());
					}
					xyPlot.setRangeAxis(i, new NumberAxis());
					xyPlot.mapDatasetToRangeAxis(i, i);
					xyPlot.mapDatasetToDomainAxis(i, i);
					xyPlot.setDataset(i, (XYDataset)datasets[i]);
					
					// set properties for domain axis
					((XYPlot)jfreechart.getPlot()).getDomainAxis(i).setLabel(chart.getProperty(prefix + PROPERTY_DOMAIN_LABEL, String.class));
					if (chart.getProperty(prefix + PROPERTY_DOMAIN_LABEL_FONT, Font.class) != null) {
						((XYPlot)jfreechart.getPlot()).getDomainAxis(i).setLabelFont(chart.getProperty(prefix + PROPERTY_DOMAIN_LABEL_FONT, Font.class));
					}
					((XYPlot)jfreechart.getPlot()).getDomainAxis(i).setLabelPaint(chart.getProperty(prefix + PROPERTY_DOMAIN_LABEL_COLOR, Color.class, Color.BLACK));
					if (chart.getProperty(prefix + PROPERTY_DOMAIN_TICK_LABELS_FONT, Font.class) != null) {
						((XYPlot)jfreechart.getPlot()).getDomainAxis(i).setTickLabelFont(chart.getProperty(prefix + PROPERTY_DOMAIN_TICK_LABELS_FONT, Font.class));
					}
					((XYPlot)jfreechart.getPlot()).getDomainAxis(i).setTickLabelsVisible(chart.getProperty(prefix + PROPERTY_DOMAIN_TICK_LABELS_VISIBLE, Boolean.class, Boolean.TRUE));
					((XYPlot)jfreechart.getPlot()).getDomainAxis(i).setTickMarksVisible(chart.getProperty(prefix + PROPERTY_DOMAIN_TICK_MARKS_VISIBLE, Boolean.class, Boolean.TRUE));
					// set properties for range axis
					((XYPlot)jfreechart.getPlot()).getRangeAxis(i).setLabel(chart.getProperty(prefix + PROPERTY_RANGE_LABEL, String.class));
					if (chart.getProperty(prefix + PROPERTY_RANGE_LABEL_FONT, Font.class) != null) {
						((XYPlot)jfreechart.getPlot()).getRangeAxis(i).setLabelFont(chart.getProperty(prefix + PROPERTY_RANGE_LABEL_FONT, Font.class));
					}
					((XYPlot)jfreechart.getPlot()).getRangeAxis(i).setLabelPaint(chart.getProperty(prefix + PROPERTY_RANGE_LABEL_COLOR, Color.class, Color.BLACK));
					if (chart.getProperty(prefix + PROPERTY_RANGE_TICK_LABELS_FONT, Font.class) != null) {
						((XYPlot)jfreechart.getPlot()).getRangeAxis(i).setTickLabelFont(chart.getProperty(prefix + PROPERTY_RANGE_TICK_LABELS_FONT, Font.class));
					}
					((XYPlot)jfreechart.getPlot()).getRangeAxis(i).setTickLabelsVisible(chart.getProperty(prefix + PROPERTY_RANGE_TICK_LABELS_VISIBLE, Boolean.class, Boolean.TRUE));
					((XYPlot)jfreechart.getPlot()).getRangeAxis(i).setTickMarksVisible(chart.getProperty(prefix + PROPERTY_RANGE_TICK_MARKS_VISIBLE, Boolean.class, Boolean.TRUE));
					((XYPlot)jfreechart.getPlot()).getRangeAxis(i).setAutoRange(chart.getProperty(prefix + PROPERTY_RANGE_AUTORANGE, Boolean.class, Boolean.TRUE));
					if (!((XYPlot)jfreechart.getPlot()).getRangeAxis(i).isAutoRange()) {
						((XYPlot)jfreechart.getPlot()).getRangeAxis(i).setLowerBound(chart.getProperty(prefix + PROPERTY_RANGE_LOWERBOUND, Double.class, 0D));
						((XYPlot)jfreechart.getPlot()).getRangeAxis(i).setUpperBound(chart.getProperty(prefix + PROPERTY_RANGE_UPPERBOUND, Double.class, 0D));
					}
					
					// set width and colors
					Object cItemWidth = chart.getProperty(prefix + ":" + PROPERTY_ITEMWIDTH_COLUMN_VALUE + ":" + 0, Number.class);
					if (cItemWidth instanceof Number) {
						if (((Number)cItemWidth).floatValue() > 0) {
							((AbstractRenderer)((XYPlot)jfreechart.getPlot()).getRenderer(i)).setSeriesStroke(0,
									new BasicStroke(((Number)cItemWidth).floatValue()));
						}
					}
					Color cItemColor = chart.getProperty(prefix + ":" + PROPERTY_ITEMCOLOR_COLUMN_VALUE + ":" + 0, Color.class);
					if (cItemColor != null) {
						((AbstractRenderer)((XYPlot)jfreechart.getPlot()).getRenderer(i)).setSeriesPaint(0, cItemColor);
					}
					
					// set markers.
					Object cDomainMarker = chart.getProperty(prefix + ":" + PROPERTY_DOMAIN_MARKER_COLUMN_VALUE, Number.class);
					if (cDomainMarker instanceof Number) {
						ValueMarker marker1 = new ValueMarker(((Number)cDomainMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
						marker1.setLabel(chart.getProperty(prefix + PROPERTY_DOMAIN_MARKER_LABEL, String.class));
						marker1.setLabelFont(new Font("Dialog", 0, 11));
						marker1.setLabelPaint(Color.DARK_GRAY);
						if (((XYPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.VERTICAL) {
							marker1.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
						} else {
							marker1.setLabelAnchor(RectangleAnchor.RIGHT);
							marker1.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
						}
						marker1.setLabelOffset(new RectangleInsets(2D, 5D, 2D, 5D));
						((XYPlot)jfreechart.getPlot()).addDomainMarker(marker1, Layer.BACKGROUND);
						ValueMarker marker2 = new ValueMarker(((Number)cDomainMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
						marker2.setLabel("" + CollectableFieldFormat.getInstance(Double.class).format("#,##0.00", marker2.getValue()));
						marker2.setLabelFont(new Font("Dialog", 0, 11));
						marker2.setLabelPaint(Color.DARK_GRAY);
						if (((XYPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.VERTICAL) {
							marker2.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
							marker2.setLabelOffset(new RectangleInsets(15D, 5D, 2D, 5D));
						} else {
							marker2.setLabelAnchor(RectangleAnchor.RIGHT);
							marker2.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
							marker2.setLabelOffset(new RectangleInsets(-25D, 5D, 2D, 5D));
						}
						((XYPlot)jfreechart.getPlot()).addDomainMarker(marker2, Layer.BACKGROUND);
					}
					Object cRangeMarker = chart.getProperty(prefix + ":" + PROPERTY_RANGE_MARKER_COLUMN_VALUE, Double.class);
					if (cRangeMarker != null && cRangeMarker instanceof Number) {
						ValueMarker marker1 = new ValueMarker(((Number)cRangeMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
						marker1.setLabel(chart.getProperty(prefix + PROPERTY_RANGE_MARKER_LABEL, String.class));
						marker1.setLabelFont(new Font("Dialog", 0, 11));
						marker1.setLabelPaint(Color.DARK_GRAY);
						if (((XYPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.HORIZONTAL) {
							marker1.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
						} else {
							marker1.setLabelAnchor(RectangleAnchor.RIGHT);
							marker1.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
						}
						marker1.setLabelOffset(new RectangleInsets(2D, 5D, 2D, 5D));
						((XYPlot)jfreechart.getPlot()).addRangeMarker(marker1, Layer.BACKGROUND);
						ValueMarker marker2 = new ValueMarker(((Number)cRangeMarker).doubleValue(), Color.LIGHT_GRAY, new BasicStroke(1.0F));
						marker2.setLabel("" + CollectableFieldFormat.getInstance(Double.class).format("#,##0.00", marker2.getValue()));
						marker2.setLabelFont(new Font("Dialog", 0, 11));
						marker2.setLabelPaint(Color.DARK_GRAY);	
						if (((XYPlot)jfreechart.getPlot()).getOrientation() == PlotOrientation.HORIZONTAL) {
							marker2.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
							marker2.setLabelOffset(new RectangleInsets(15D, 5D, 2D, 5D));
						} else {
							marker2.setLabelAnchor(RectangleAnchor.RIGHT);
							marker2.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
							marker2.setLabelOffset(new RectangleInsets(-25D, 5D, 2D, 5D));
						}
						((XYPlot)jfreechart.getPlot()).addRangeMarker(marker2, Layer.BACKGROUND);
					}
					
					if (chart.getProperty(prefix + PROPERTY_MOUSE_MARKER_SELECTED, Boolean.class, Boolean.FALSE)) {
						renderer.setBaseToolTipGenerator(
								new StandardXYToolTipGenerator("{2}", SpringLocaleDelegate.getInstance().getDateFormat(), SpringLocaleDelegate.getInstance().getNumberFormat()));
					}
					if (chart.getProperty(prefix + PROPERTY_SHOW_MARKER_SELECTED, Boolean.class, Boolean.FALSE)) {
						renderer.setBaseItemLabelGenerator(
								new StandardXYItemLabelGenerator("{2}", SpringLocaleDelegate.getInstance().getDateFormat(), SpringLocaleDelegate.getInstance().getNumberFormat()));
						renderer.setBaseItemLabelsVisible(true);
					}
					
				}
				
				xyPlot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
			    
				setCommonChartProperties(chart, jfreechart);

				return jfreechart;
			}
			@Override
			public List<Pair<String, String>> getChartProperties(Chart c, JFreeChart chart) {
				List<Pair<String, String>> result = new ArrayList<Pair<String,String>>();
				
				result.addAll(getCommonChartProperties(chart));

				if (!(chart.getPlot() instanceof XYPlot)) {
					return result;
				}
				
				// set properties for xy plot
				result.add(new Pair<String, String>(Chart.PROPERTY_PLOT_ORIENTATION, "" + ((XYPlot)chart.getPlot()).getOrientation().toString()));

				String combinedPrefixes = c.getProperty(PROPERTY_COMBINED_PREFIXES, String.class);
				String[] prefixes = combinedPrefixes == null ? new String[0] : combinedPrefixes.split(":");
				for (int i = 0; i < prefixes.length; i++) {
					String prefix = prefixes[i];
					if (StringUtils.isNullOrEmpty(prefix)) {
						continue;
					}
					
					// set properties for domain axis
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_DOMAIN_LABEL, "" + ((XYPlot)chart.getPlot()).getDomainAxis(i).getLabel()));
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_DOMAIN_LABEL_FONT, "" + fontToString(((XYPlot)chart.getPlot()).getDomainAxis(i).getLabelFont())));
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_DOMAIN_LABEL_COLOR, "" + ((Color)((XYPlot)chart.getPlot()).getDomainAxis(i).getLabelPaint()).getRGB()));
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_DOMAIN_TICK_LABELS_FONT, "" + fontToString(((XYPlot)chart.getPlot()).getDomainAxis(i).getTickLabelFont())));
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_DOMAIN_TICK_LABELS_VISIBLE, "" + ((XYPlot)chart.getPlot()).getDomainAxis(i).isTickLabelsVisible()));
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_DOMAIN_TICK_MARKS_VISIBLE, "" + ((XYPlot)chart.getPlot()).getDomainAxis(i).isTickMarksVisible()));
					// set properties for range axis
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_RANGE_LABEL, "" + ((XYPlot)chart.getPlot()).getRangeAxis(i).getLabel()));
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_RANGE_LABEL_FONT, "" + fontToString(((XYPlot)chart.getPlot()).getRangeAxis(i).getLabelFont())));
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_RANGE_LABEL_COLOR, "" + ((Color)((XYPlot)chart.getPlot()).getRangeAxis(i).getLabelPaint()).getRGB()));
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_RANGE_TICK_LABELS_FONT, "" + fontToString(((XYPlot)chart.getPlot()).getRangeAxis(i).getTickLabelFont())));
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_RANGE_TICK_LABELS_VISIBLE, "" + ((XYPlot)chart.getPlot()).getRangeAxis(i).isTickLabelsVisible()));
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_RANGE_TICK_MARKS_VISIBLE, "" + ((XYPlot)chart.getPlot()).getRangeAxis(i).isTickMarksVisible()));
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_RANGE_AUTORANGE, "" + ((XYPlot)chart.getPlot()).getRangeAxis(i).isAutoRange()));
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_RANGE_LOWERBOUND, "" + ((XYPlot)chart.getPlot()).getRangeAxis(i).getLowerBound()));
					result.add(new Pair<String, String>(prefix + Chart.PROPERTY_RANGE_UPPERBOUND, "" + ((XYPlot)chart.getPlot()).getRangeAxis(i).getUpperBound()));
				}
				
				return result;
			}			
			@Override
			public ChartColumn[] getValueColumnDesc(boolean withScaleColumn) {
				return new ChartColumn[] {};
			}
			@Override
			public ChartColumn[] getDomainColumnDesc() {
				return new ChartColumn[] {};
			}
			public boolean isCombinedChart() {
				return true;
			}
			public ChartFunction[] getCombinedChartFunctions() {
				return new ChartFunction[] {XYSERIESCHART, XYBARCHART, XYSCATTERCHART, XYSTEPCHART, TIMESERIESCHART};
			}
			@Override
			public JTabbedPanel[] getCustomPlotEditorPanels(Chart chart, String prefix) {
				CustomChartEditor editor = new DefaultCombinedPlotEditor(chart);
				CustomChartEditor editorAppearance = new DefaultCombinedPlotAppearanceEditor(chart);
				return new JTabbedPanel[] {
						new JTabbedPanel(editor.getName(), editor),
						new JTabbedPanel(editorAppearance.getName(), editorAppearance)
				};
			}
		};

		public abstract JFreeChart createChart(Chart chart);
		protected abstract Dataset[] createDataset(Chart chart);
		protected Dataset[] createDataset(Chart chart, String prefix) {
			return createDataset(chart);
		}
		
		public abstract ChartColumn[] getValueColumnDesc(boolean withScaleColumn);
		public abstract ChartColumn[] getDomainColumnDesc();
		
		private static class DefaultDrawingSupplier extends org.jfree.chart.plot.DefaultDrawingSupplier {
			public DefaultDrawingSupplier() {
				super(new Paint[] {
			            new Color(0x55, 0x55, 0xFF),
			            new Color(0xFF, 0x55, 0x55),
			            new Color(0x55, 0xFF, 0x55),
			            ChartColor.DARK_BLUE,
			            ChartColor.DARK_RED,
			            ChartColor.DARK_GREEN,
			            ChartColor.LIGHT_BLUE,
			            ChartColor.LIGHT_RED,
			            ChartColor.LIGHT_GREEN,
			            ChartColor.VERY_DARK_BLUE,
			            ChartColor.VERY_DARK_RED,
			            ChartColor.VERY_DARK_GREEN,
			            ChartColor.VERY_LIGHT_BLUE,
			            ChartColor.VERY_LIGHT_RED,
			            ChartColor.VERY_LIGHT_GREEN
			            
			        },
	            DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE,
	            DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE,
	            DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE,
	            createStandardSeriesShapes()
	            );
			}
		    private static int[] intArray(double a, double b, double c) {
		        return new int[] {(int) a, (int) b, (int) c};
		    }	
		    private static int[] intArray(double a, double b, double c, double d) {
		        return new int[] {(int) a, (int) b, (int) c, (int) d};
		    }
		    
			public static Shape[] createStandardSeriesShapes() {
		        Shape[] result = new Shape[10];

		        double size = 6.0;
		        double delta = size / 2.0;
		        int[] xpoints = null;
		        int[] ypoints = null;

		        // up-pointing triangle
		        xpoints = intArray(0.0, delta, -delta);
		        ypoints = intArray(-delta, delta, delta);
		        result[0] = new Polygon(xpoints, ypoints, 3);

		        // square
		        result[1] = new Rectangle2D.Double(-delta, -delta, size, size);
		        
		        // circle
		        result[2] = new Ellipse2D.Double(-delta, -delta, size, size);

		        // diamond
		        xpoints = intArray(0.0, delta, 0.0, -delta);
		        ypoints = intArray(-delta, 0.0, delta, 0.0);
		        result[3] = new Polygon(xpoints, ypoints, 4);

		        // horizontal rectangle
		        result[4] = new Rectangle2D.Double(-delta, -delta / 2, size, size / 2);

		        // down-pointing triangle
		        xpoints = intArray(-delta, +delta, 0.0);
		        ypoints = intArray(-delta, -delta, delta);
		        result[5] = new Polygon(xpoints, ypoints, 3);

		        // horizontal ellipse
		        result[6] = new Ellipse2D.Double(-delta, -delta / 2, size, size / 2);

		        // right-pointing triangle
		        xpoints = intArray(-delta, delta, -delta);
		        ypoints = intArray(-delta, 0.0, delta);
		        result[7] = new Polygon(xpoints, ypoints, 3);

		        // vertical rectangle
		        result[8] = new Rectangle2D.Double(-delta / 2, -delta, size / 2, size);

		        // left-pointing triangle
		        xpoints = intArray(-delta, delta, delta);
		        ypoints = intArray(0.0, -delta, +delta);
		        result[9] = new Polygon(xpoints, ypoints, 3);

		        return result;
		    }
		}

		static class DefaultLegendEditor extends CustomChartEditor implements IChart, ActionListener {

			private final String prefix;
		    private JTextField label;
		    private Font labelFont;
		    private PaintSample labelPaintSample;
		    private JTextField labelFontField;
		    protected ResourceBundle localizationResources
		            = ResourceBundleWrapper.getBundle(
		                    "org.jfree.chart.editor.LocalizationBundle");
		    public DefaultLegendEditor(Chart chart) {
		    	this(chart, "");
		    }
		    public DefaultLegendEditor(Chart chart, String prefix) {
		    	setName(localizationResources.getString("Legend"));
		    	
		    	this.prefix = prefix;
		    	
		        this.labelFont = new Font("Dialog", 1, 12);
		        this.labelPaintSample = new PaintSample(Color.DARK_GRAY);
		        this.labelFont = new Font("Dialog", 1, 12);
		        this.labelPaintSample = new PaintSample(Color.DARK_GRAY);

		        setLayout(new BorderLayout());

		        JPanel domain = new JPanel(new BorderLayout());
		        domain.setBorder(
		            BorderFactory.createTitledBorder(
		                BorderFactory.createEtchedBorder(),
		                localizationResources.getString("General")
		            )
		        );

		        JPanel interior = new JPanel(new LCBLayout(5));
		        interior.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		        interior.add(new JLabel(localizationResources.getString("Label")));
		        this.label = new JTextField(chart.getProperty(prefix + Chart.PROPERTY_LEGEND_LABEL, String.class));
		        interior.add(this.label);
		        interior.add(new JPanel());

		        interior.add(new JLabel(localizationResources.getString("Font")));
		        this.labelFontField = new FontDisplayField(this.labelFont);
		        interior.add(this.labelFontField);
		        JButton b = new JButton(localizationResources.getString("Select..."));
		        b.setActionCommand("SelectLabelFont");
		        b.addActionListener(this);
		        b.setEnabled(false);
		        interior.add(b);

		        interior.add(new JLabel(localizationResources.getString("Paint")));
		        interior.add(this.labelPaintSample);
		        b = new JButton(localizationResources.getString("Select..."));
		        b.setActionCommand("SelectLabelPaint");
		        b.addActionListener(this);
		        b.setEnabled(false);
		        interior.add(b);

		        domain.add(interior);

		        add(domain, BorderLayout.NORTH);
		    }
		    public void actionPerformed(ActionEvent event) {
		        String command = event.getActionCommand();
		        if (command.equals("SelectLabelFont")) {
		            attemptLabelFontSelection();
		        }
		        else if (command.equals("SelectLabelPaint")) {
		            attemptModifyLabelPaint();
		        }
			}
		    private void attemptLabelFontSelection() {
		        FontChooserPanel panel = new FontChooserPanel(this.labelFont);
		        int result = JOptionPane.showConfirmDialog(this, panel,
		            localizationResources.getString("Font_Selection"),
		            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		        if (result == JOptionPane.OK_OPTION) {
		            this.labelFont = panel.getSelectedFont();
		            this.labelFontField.setText(
		                this.labelFont.getFontName() + " " + this.labelFont.getSize()
		            );
		        }
		    }
		    private void attemptModifyLabelPaint() {
		        Color c;
		        c = JColorChooser.showDialog(
		            this, localizationResources.getString("Label_Color"), Color.blue
		        );
		        if (c != null) {
		            this.labelPaintSample.setPaint(c);
		        }
		    }		    @Override
		    public List<Pair<String, String>> applyChartProperties(JFreeChart chart) {
				List<Pair<String, String>> result = new ArrayList<Pair<String,String>>();

				result.add(new Pair<String, String>(prefix + PROPERTY_LEGEND_LABEL, "" + this.label.getText()));
				//result.add(new Pair<String, String>(prefix + Chart.PROPERTY_LEGEND_LABEL_FONT, "" + Chart.fontToString(this.labelFont)));
				//result.add(new Pair<String, String>(prefix + Chart.PROPERTY_LEGEND_LABEL_COLOR, "" + ((Color)this.labelPaintSample.getPaint()).getRGB()));
		    	
				return result;
		    }
		}
		static class DefaultPlotMarkerEditor extends CustomChartEditor implements ActionListener {

			private final String prefix;
		    private JTextField labelDomainMarker;
		    private Font labelDomainMarkerFont;
		    private PaintSample labelDomainMarkerPaintSample;
		    private JTextField labelDomainMarkerFontField;
		    private JTextField labelRangeMarker;
		    private Font labelRangeMarkerFont;
		    private PaintSample labelRangeMarkerPaintSample;
		    private JTextField labelRangeMarkerFontField;
		    private JCheckBox labelShowMarker;
		    private JCheckBox labelMouseMarker;
		    protected ResourceBundle localizationResources
		            = ResourceBundleWrapper.getBundle(
		                    "org.jfree.chart.editor.LocalizationBundle");
		    public DefaultPlotMarkerEditor(Chart chart) {
		    	this(chart, "");
		    }
		    public DefaultPlotMarkerEditor(Chart chart, String prefix) {
		    	setName(SpringLocaleDelegate.getInstance().getMessage("Chart.plotMarker.properties.name", "Plot Markierungen"));
		    	
		    	this.prefix = prefix;
		    	
		        this.labelDomainMarkerFont = new Font("Dialog", 1, 12);
		        this.labelDomainMarkerPaintSample = new PaintSample(Color.DARK_GRAY);
		        this.labelRangeMarkerFont = new Font("Dialog", 1, 12);
		        this.labelRangeMarkerPaintSample = new PaintSample(Color.DARK_GRAY);

		        setLayout(new BorderLayout());

		        JPanel domain = new JPanel(new BorderLayout());
		        domain.setBorder(
		            BorderFactory.createTitledBorder(
		                BorderFactory.createEtchedBorder(),
		                SpringLocaleDelegate.getInstance().getMessage("Chart.plotMarker.properties.domain", "Plot Markierungen (Rubrik)")
		            )
		        );

		        JPanel interiorDomain = new JPanel(new LCBLayout(5));
		        interiorDomain.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		        interiorDomain.add(new JLabel(localizationResources.getString("Label")));
		        this.labelDomainMarker = new JTextField(chart.getProperty(prefix + Chart.PROPERTY_DOMAIN_MARKER_LABEL, String.class));
		        interiorDomain.add(this.labelDomainMarker);
		        interiorDomain.add(new JPanel());

		        interiorDomain.add(new JLabel(localizationResources.getString("Font")));
		        this.labelDomainMarkerFontField = new FontDisplayField(this.labelDomainMarkerFont);
		        interiorDomain.add(this.labelDomainMarkerFontField);
		        JButton bDomain = new JButton(localizationResources.getString("Select..."));
		        bDomain.setActionCommand("SelectLabelFont");
		        bDomain.addActionListener(this);
		        bDomain.setEnabled(false);
		        interiorDomain.add(bDomain);

		        interiorDomain.add(new JLabel(localizationResources.getString("Paint")));
		        interiorDomain.add(this.labelDomainMarkerPaintSample);
		        bDomain = new JButton(localizationResources.getString("Select..."));
		        bDomain.setActionCommand("SelectLabelPaint");
		        bDomain.addActionListener(this);
		        bDomain.setEnabled(false);
		        interiorDomain.add(bDomain);

		        domain.add(interiorDomain);

		        JPanel range = new JPanel(new BorderLayout());
		        range.setBorder(
		            BorderFactory.createTitledBorder(
		                BorderFactory.createEtchedBorder(),
		                SpringLocaleDelegate.getInstance().getMessage("Chart.plotMarker.properties.range", "Plot Markierungen (Wertebereich)")
			        )
		        );

		        JPanel interiorRange = new JPanel(new LCBLayout(5));
		        interiorRange.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		        interiorRange.add(new JLabel(localizationResources.getString("Label")));
		        this.labelRangeMarker = new JTextField(chart.getProperty(prefix + Chart.PROPERTY_RANGE_MARKER_LABEL, String.class));
		        interiorRange.add(this.labelRangeMarker);
		        interiorRange.add(new JPanel());

		        interiorRange.add(new JLabel(localizationResources.getString("Font")));
		        this.labelRangeMarkerFontField = new FontDisplayField(this.labelRangeMarkerFont);
		        interiorRange.add(this.labelRangeMarkerFontField);
		        JButton bRange = new JButton(localizationResources.getString("Select..."));
		        bRange.setActionCommand("SelectLabelFont");
		        bRange.addActionListener(this);
		        bRange.setEnabled(false);
		        interiorRange.add(bRange);

		        interiorRange.add(new JLabel(localizationResources.getString("Paint")));
		        interiorRange.add(this.labelRangeMarkerPaintSample);
		        bRange = new JButton(localizationResources.getString("Select..."));
		        bRange.setActionCommand("SelectLabelPaint");
		        bRange.addActionListener(this);
		        bRange.setEnabled(false);
		        interiorRange.add(bRange);

		        range.add(interiorRange);

		        JPanel general = new JPanel(new BorderLayout());
		        general.setBorder(
		            BorderFactory.createTitledBorder(
		                BorderFactory.createEtchedBorder(),
		                SpringLocaleDelegate.getInstance().getMessage("Chart.plotMarker.properties.general", "Allgemein")
			        )
		        );

		        JPanel interiorGeneral = new JPanel(new LCBLayout(5));
		        interiorGeneral.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		        this.labelShowMarker = new JCheckBox(SpringLocaleDelegate.getInstance().getMessage("Chart.plotMarker.properties.drawValues", "Markierungen immer zeichnen"), 
		        		chart.getProperty(prefix + Chart.PROPERTY_SHOW_MARKER_SELECTED, Boolean.class) == null
		        			? Boolean.FALSE : chart.getProperty(prefix + Chart.PROPERTY_SHOW_MARKER_SELECTED, Boolean.class));
		        interiorGeneral.add(labelShowMarker);
		        this.labelMouseMarker = new JCheckBox(SpringLocaleDelegate.getInstance().getMessage("Chart.plotMarker.properties.drawMouseOver", "Markierungen zeichnen bei Mouse-over"), 
		        		chart.getProperty(prefix + Chart.PROPERTY_MOUSE_MARKER_SELECTED, Boolean.class) == null
		        			? Boolean.FALSE : chart.getProperty(prefix + Chart.PROPERTY_MOUSE_MARKER_SELECTED, Boolean.class));
		        interiorGeneral.add(labelMouseMarker);
		        interiorGeneral.add(new JLabel(""));
		        interiorGeneral.add(new JPanel());

		        general.add(interiorGeneral);
		        
		        JPanel marker = new JPanel(new BorderLayout());
		        marker.add(domain, BorderLayout.NORTH);
		        marker.add(range, BorderLayout.CENTER);

		        add(general, BorderLayout.NORTH);
		        add(marker, BorderLayout.CENTER);
		    }
		    public void actionPerformed(ActionEvent event) {
		        String command = event.getActionCommand();
		        if (command.equals("SelectLabelDomainFont")) {
		            attemptLabelFontDomainSelection();
		        }
		        else if (command.equals("SelectLabelDomainPaint")) {
		            attemptModifyLabelDomainPaint();
		        }
		        else if (command.equals("SelectLabelRangeFont")) {
		            attemptLabelFontRangeSelection();
		        }
		        else if (command.equals("SelectLabelRangePaint")) {
		            attemptModifyLabelRangePaint();
		        }
		    }
		    private void attemptLabelFontDomainSelection() {
		        FontChooserPanel panel = new FontChooserPanel(this.labelDomainMarkerFont);
		        int result = JOptionPane.showConfirmDialog(this, panel,
		            localizationResources.getString("Font_Selection"),
		            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		        if (result == JOptionPane.OK_OPTION) {
		            this.labelDomainMarkerFont = panel.getSelectedFont();
		            this.labelDomainMarkerFontField.setText(
		                this.labelDomainMarkerFont.getFontName() + " " + this.labelDomainMarkerFont.getSize()
		            );
		        }
		    }
		    private void attemptModifyLabelDomainPaint() {
		        Color c;
		        c = JColorChooser.showDialog(
		            this, localizationResources.getString("Label_Color"), Color.blue
		        );
		        if (c != null) {
		            this.labelDomainMarkerPaintSample.setPaint(c);
		        }
		    }
		    private void attemptLabelFontRangeSelection() {
		        FontChooserPanel panel = new FontChooserPanel(this.labelRangeMarkerFont);
		        int result = JOptionPane.showConfirmDialog(this, panel,
		            localizationResources.getString("Font_Selection"),
		            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		        if (result == JOptionPane.OK_OPTION) {
		            this.labelRangeMarkerFont = panel.getSelectedFont();
		            this.labelRangeMarkerFontField.setText(
		                this.labelRangeMarkerFont.getFontName() + " " + this.labelRangeMarkerFont.getSize()
		            );
		        }
		    }
		    private void attemptModifyLabelRangePaint() {
		        Color c;
		        c = JColorChooser.showDialog(
		            this, localizationResources.getString("Label_Color"), Color.blue
		        );
		        if (c != null) {
		            this.labelRangeMarkerPaintSample.setPaint(c);
		        }
		    }						
		    @Override
		    public List<Pair<String, String>> applyChartProperties(JFreeChart chart) {
				List<Pair<String, String>> result = new ArrayList<Pair<String,String>>();

				result.add(new Pair<String, String>(prefix + Chart.PROPERTY_SHOW_MARKER_SELECTED, "" + this.labelShowMarker.isSelected()));
				result.add(new Pair<String, String>(prefix + Chart.PROPERTY_MOUSE_MARKER_SELECTED, "" + this.labelMouseMarker.isSelected()));
				
				result.add(new Pair<String, String>(prefix + Chart.PROPERTY_DOMAIN_MARKER_LABEL, "" + this.labelDomainMarker.getText()));
				//result.add(new Pair<String, String>(prefix + Chart.PROPERTY_DOMAIN_MARKER_LABEL_FONT, "" + Chart.fontToString(this.labelDomainMarkerFont)));
				//result.add(new Pair<String, String>(prefix + Chart.PROPERTY_DOMAIN_MARKER_LABEL_COLOR, "" + ((Color)this.labelDomainMarkerPaintSample.getPaint()).getRGB()));
				result.add(new Pair<String, String>(prefix + Chart.PROPERTY_RANGE_MARKER_LABEL, "" + this.labelRangeMarker.getText()));
				//result.add(new Pair<String, String>(prefix + Chart.PROPERTY_RANGE_MARKER_LABEL_FONT, "" + Chart.fontToString(this.labelRangeMarkerFont)));
				//result.add(new Pair<String, String>(prefix + Chart.PROPERTY_RANGE_MARKER_LABEL_COLOR, "" + ((Color)this.labelRangeMarkerPaintSample.getPaint()).getRGB()));
		    	
				return result;
		    }
		}
		static class DefaultCombinedPlotEditor extends CustomChartEditor {

			protected static ResourceBundle localizationResources
		            = ResourceBundleWrapper.getBundle(
		                    "org.jfree.chart.editor.LocalizationBundle");

			private final Object[] plotEditors;
			private  Map<String, JTabbedPanel[]> customPlotEditors = new HashMap<String, JTabbedPanel[]>();

		    public DefaultCombinedPlotEditor(Chart chart) {
		    	Plot plot = chart.getChartPanel().getChart().getPlot();

		    	setName(localizationResources.getString("Plot"));
		    	
		        setLayout(new BorderLayout());

		        // create a panel for the settings...
		        JPanel panel = new JPanel(new BorderLayout());

		        JTabbedPane tabs = new JTabbedPane();
		        tabs.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
				
		        String combinedPrefixes = chart.getProperty(Chart.PROPERTY_COMBINED_PREFIXES, String.class);
				String[] prefixes = combinedPrefixes.split(":");
				
				plotEditors = new Object[prefixes.length];
				
				for (int i = 0; i < prefixes.length; i++) {
					String prefix = prefixes[i];
					if (StringUtils.isNullOrEmpty(prefix)) {
						continue;
					}
					
					JTabbedPane tabsAxis = new JTabbedPane();

					tabsAxis.setOpaque(true);
					tabsAxis.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
					 
					try {
						Axis domainAxis = null;
				        if (plot instanceof CategoryPlot) {
				            domainAxis = ((CategoryPlot) plot).getDomainAxis(i);
				        }
				        else if (plot instanceof XYPlot) {
				            domainAxis = ((XYPlot) plot).getDomainAxis(i);
				        }
				        final ClassLoader cl = LangUtils.getClassLoaderThatWorksForWebStart();
						Class domainAxisEditorClass = cl.loadClass("org.jfree.chart.editor.DefaultAxisEditor");
						Method domainAxisEditorInstanceMethod = domainAxisEditorClass.getDeclaredMethod("getInstance", Axis.class);
						domainAxisEditorInstanceMethod.setAccessible(true);
						JPanel domainAxisPropertyPanel = (JPanel) domainAxisEditorInstanceMethod.invoke(null, domainAxis);
			            domainAxisPropertyPanel.setBorder(
			                    BorderFactory.createEmptyBorder(2, 2, 2, 2));
						tabsAxis.add(localizationResources.getString("Domain_Axis"), domainAxisPropertyPanel);
						
						Axis rangeAxis = null;
				        if (plot instanceof CategoryPlot) {
				        	rangeAxis = ((CategoryPlot) plot).getRangeAxis(i);
				        }
				        else if (plot instanceof XYPlot) {
				        	rangeAxis = ((XYPlot) plot).getRangeAxis(i);
				        }
						Class rangeAxisEditorClass = cl.loadClass("org.jfree.chart.editor.DefaultAxisEditor");
						Method rangeAxisEditorInstanceMethod = rangeAxisEditorClass.getDeclaredMethod("getInstance", Axis.class);
						rangeAxisEditorInstanceMethod.setAccessible(true);
						JPanel rangeAxisPropertyPanel = (JPanel) rangeAxisEditorInstanceMethod.invoke(null, rangeAxis);
						rangeAxisPropertyPanel.setBorder(
			                    BorderFactory.createEmptyBorder(2, 2, 2, 2));
						tabsAxis.add(localizationResources.getString("Range_Axis"), rangeAxisPropertyPanel); 

						// add custom editors.
						ChartFunction cFunction = ChartFunction.valueOf(prefix.split("\\.")[0]);
						JTabbedPanel[] cPlotEditors = cFunction.getCustomPlotEditorPanels(chart, prefix);
						JTabbedPanel[] cPlotEditorsArray = new JTabbedPanel[cPlotEditors.length];
						for (int j = 0; j < cPlotEditors.length; j++) {
							cPlotEditorsArray[j] = cPlotEditors[j];
						}
						customPlotEditors.put(prefix, cPlotEditorsArray);
						
						for (int j = 0; j < cPlotEditorsArray.length; j++) {
							JTabbedPanel customEditor = cPlotEditorsArray[j];
							tabsAxis.add(customEditor.getTitle(), customEditor);
						}
						plotEditors[i] = new JPanel[] {domainAxisPropertyPanel, rangeAxisPropertyPanel};
						
						tabs.add(cFunction.name(), tabsAxis);
					} catch (Exception e) {
						// ignore...
					}
				}

		        panel.add(tabs);

		        add(panel);
		    }
			@Override
			public List<Pair<String, String>> applyChartProperties(JFreeChart chart) {
				Plot plot = chart.getPlot();

				for (int i = 0; i < plotEditors.length; i++) {
					try {
						Axis domainAxis = null;
			            if (plot instanceof CategoryPlot) {
			                CategoryPlot p = (CategoryPlot) plot;
			                domainAxis = p.getDomainAxis(i);
			            }
			            else if (plot instanceof XYPlot) {
			                XYPlot p = (XYPlot) plot;
			                domainAxis = p.getDomainAxis(i);
			            }
			            if (domainAxis != null) {
			            	Class domainAxisEditorClass = LangUtils.getClassLoaderThatWorksForWebStart().loadClass(
			            			"org.jfree.chart.editor.DefaultAxisEditor");
							Method domainAxisEditorInstanceMethod = domainAxisEditorClass.getDeclaredMethod("setAxisProperties", Axis.class);
							domainAxisEditorInstanceMethod.setAccessible(true);
							domainAxisEditorInstanceMethod.invoke(((JPanel[])plotEditors[i])[0], domainAxis);
			            }
			            
			            Axis rangeAxis = null;
			            if (plot instanceof CategoryPlot) {
			                CategoryPlot p = (CategoryPlot) plot;
			                rangeAxis = p.getRangeAxis(i);
			            }
			            else if (plot instanceof XYPlot) {
			                XYPlot p = (XYPlot) plot;
			                rangeAxis = p.getRangeAxis(i);
			            }
			            if (rangeAxis != null) {
			            	Class rangeAxisEditorClass = LangUtils.getClassLoaderThatWorksForWebStart().loadClass(
			            			"org.jfree.chart.editor.DefaultAxisEditor");
							Method rangeAxisEditorInstanceMethod = rangeAxisEditorClass.getDeclaredMethod("setAxisProperties", Axis.class);
							rangeAxisEditorInstanceMethod.setAccessible(true);
							rangeAxisEditorInstanceMethod.invoke(((JPanel[])plotEditors[i])[1], rangeAxis);
			            }
					} catch (Exception e) {
						// ignore...
					}	
				}
				
				List<Pair<String, String>> result = new ArrayList<Pair<String,String>>();
				for (JTabbedPanel[] cPlotEditors : customPlotEditors.values()) {
					for (int i = 0; i < cPlotEditors.length; i++) {
						JTabbedPanel customEditor = cPlotEditors[i];
						result.addAll(customEditor.applyChartProperties(chart));
					}
				}
				return result;
			}
		}
		static class DefaultCombinedPlotAppearanceEditor extends CustomChartEditor implements ActionListener {

		    private static final String[] ORIENTATIONS = {"Vertical", "Horizontal"};
		    private static final int ORIENTATION_VERTICAL = 0;
		    private static final int ORIENTATION_HORIZONTAL = 1;

		    private PaintSample backgroundPaintSample;
		    private StrokeSample outlineStrokeSample;
		    private PaintSample outlinePaintSample;

		    private StrokeSample[] availableStrokeSamples;
		    
		    private PlotOrientation plotOrientation;
		    private JComboBox orientationCombo;

		    protected static ResourceBundle localizationResources
		            = ResourceBundleWrapper.getBundle(
		                    "org.jfree.chart.editor.LocalizationBundle");

		    public DefaultCombinedPlotAppearanceEditor(Chart chart) {
		    	Plot plot = chart.getChartPanel().getChart().getPlot();
		    	
		    	setName(localizationResources.getString("Appearance"));
		    
		        this.backgroundPaintSample = new PaintSample(plot.getBackgroundPaint());
		        this.outlineStrokeSample = new StrokeSample(plot.getOutlineStroke());
		        this.outlinePaintSample = new PaintSample(plot.getOutlinePaint());
		        if (plot instanceof CategoryPlot) {
		            this.plotOrientation = ((CategoryPlot) plot).getOrientation();
		        }
		        else if (plot instanceof XYPlot) {
		            this.plotOrientation = ((XYPlot) plot).getOrientation();
		        }

		        setLayout(new BorderLayout());

		        this.availableStrokeSamples = new StrokeSample[4];
		        this.availableStrokeSamples[0] = new StrokeSample(null);
		        this.availableStrokeSamples[1] = new StrokeSample(
		                new BasicStroke(1.0f));
		        this.availableStrokeSamples[2] = new StrokeSample(
		                new BasicStroke(2.0f));
		        this.availableStrokeSamples[3] = new StrokeSample(
		                new BasicStroke(3.0f));

		        // create a panel for the settings...
		        JPanel panel = new JPanel(new BorderLayout());

		        JPanel general = new JPanel(new BorderLayout());
		        general.setBorder(BorderFactory.createTitledBorder(
		                localizationResources.getString("General")));

		        JPanel interior = new JPanel(new LCBLayout(7));
		        interior.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

		        interior.add(new JLabel(localizationResources.getString(
		                "Outline_stroke")));
		        JButton button = new JButton(localizationResources.getString(
		                "Select..."));
		        button.setActionCommand("OutlineStroke");
		        button.addActionListener(this);
		        interior.add(this.outlineStrokeSample);
		        interior.add(button);

		        interior.add(new JLabel(localizationResources.getString(
		                "Outline_Paint")));
		        button = new JButton(localizationResources.getString("Select..."));
		        button.setActionCommand("OutlinePaint");
		        button.addActionListener(this);
		        interior.add(this.outlinePaintSample);
		        interior.add(button);

		        interior.add(new JLabel(localizationResources.getString(
		                "Background_paint")));
		        button = new JButton(localizationResources.getString("Select..."));
		        button.setActionCommand("BackgroundPaint");
		        button.addActionListener(this);
		        interior.add(this.backgroundPaintSample);
		        interior.add(button);

		        if (this.plotOrientation != null) {
		            boolean isVertical = this.plotOrientation.equals(
		                    PlotOrientation.VERTICAL);
		            int index = isVertical ? ORIENTATION_VERTICAL
		                    : ORIENTATION_HORIZONTAL;
		            interior.add(new JLabel(localizationResources.getString(
		                    "Orientation")));
		            this.orientationCombo = new JComboBox(ORIENTATIONS);
		            this.orientationCombo.setSelectedIndex(index);
		            this.orientationCombo.setActionCommand("Orientation");
		            this.orientationCombo.addActionListener(this);
		            interior.add(new JPanel());
		            interior.add(this.orientationCombo);
		        }

		        general.add(interior, BorderLayout.NORTH);

		        JPanel appearance = new JPanel(new BorderLayout());
		        appearance.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		        appearance.add(general, BorderLayout.NORTH);

		        panel.add(appearance);

		        add(panel);
		    }
		    public void actionPerformed(ActionEvent event) {
		        String command = event.getActionCommand();
		        if (command.equals("BackgroundPaint")) {
		            attemptBackgroundPaintSelection();
		        }
		        else if (command.equals("OutlineStroke")) {
		            attemptOutlineStrokeSelection();
		        }
		        else if (command.equals("OutlinePaint")) {
		            attemptOutlinePaintSelection();
		        }
		        else if (command.equals("Orientation")) {
		            attemptOrientationSelection();
		        }
		    }
		    private void attemptBackgroundPaintSelection() {
		        Color c;
		        c = JColorChooser.showDialog(this, localizationResources.getString(
		                "Background_Color"), Color.blue);
		        if (c != null) {
		            this.backgroundPaintSample.setPaint(c);
		        }
		    }
		    private void attemptOutlineStrokeSelection() {
		        StrokeChooserPanel panel = new StrokeChooserPanel(
		                this.outlineStrokeSample, this.availableStrokeSamples);
		        int result = JOptionPane.showConfirmDialog(this, panel,
		                localizationResources.getString("Stroke_Selection"),
		                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		        if (result == JOptionPane.OK_OPTION) {
		            this.outlineStrokeSample.setStroke(panel.getSelectedStroke());
		        }
		    }
		    private void attemptOutlinePaintSelection() {
		        Color c;
		        c = JColorChooser.showDialog(this, localizationResources.getString(
		                "Outline_Color"), Color.blue);
		        if (c != null) {
		            this.outlinePaintSample.setPaint(c);
		        }
		    }
		    private void attemptOrientationSelection() {
		        int index = this.orientationCombo.getSelectedIndex();

		        if (index == ORIENTATION_VERTICAL) {
		            this.plotOrientation = PlotOrientation.VERTICAL;
		        }
		        else {
		            this.plotOrientation = PlotOrientation.HORIZONTAL;
		        }
		    }
			@Override
			public List<Pair<String, String>> applyChartProperties(JFreeChart chart) {
				Plot plot = chart.getPlot();

				 // set the plot properties...
		        plot.setOutlinePaint(this.outlinePaintSample.getPaint());
		        plot.setOutlineStroke(this.outlineStrokeSample.getStroke());
		        plot.setBackgroundPaint(this.backgroundPaintSample.getPaint());

		        if (this.plotOrientation != null) {
		            if (plot instanceof CategoryPlot) {
		                CategoryPlot p = (CategoryPlot) plot;
		                p.setOrientation(this.plotOrientation);
		            }
		            else if (plot instanceof XYPlot) {
		                XYPlot p = (XYPlot) plot;
		                p.setOrientation(this.plotOrientation);
		            }
		        }
				return Collections.EMPTY_LIST;
			}
		}

		public abstract static class CustomChartEditor extends JPanel {
			public abstract List<Pair<String, String>> applyChartProperties(JFreeChart chart);
		}
		public static class JTabbedPanel extends JPanel {
			private String title;
			private CustomChartEditor editor;
			public JTabbedPanel(String title, CustomChartEditor editor) {
				this.title = title;
				this.editor = editor;
				
				setLayout(new BorderLayout());
				editor.setBorder(
		                BorderFactory.createEmptyBorder(2, 2, 2, 2));
				add(editor, BorderLayout.CENTER);
			}
			public String getTitle() {
				return title;
			}
			public List<Pair<String, String>> applyChartProperties(JFreeChart chart) {
				return editor.applyChartProperties(chart);
			}
		}
		public JTabbedPanel[] getCustomPlotEditorPanels(Chart chart) {
			return getCustomPlotEditorPanels(chart, "");
		}
		public JTabbedPanel[] getCustomPlotEditorPanels(Chart chart, String prefix) {
			return new JTabbedPanel[0];
		}
		public boolean isCombinedChart() {
			return false;
		}
		public ChartFunction[] getCombinedChartFunctions() {
			return new ChartFunction[0];
		}
		
		public abstract List<Pair<String, String>> getChartProperties(Chart chart, JFreeChart jfreechart);
		
		private static List<Pair<String, String>> getCommonChartProperties(JFreeChart chart) {
			List<Pair<String, String>> result = new ArrayList<Pair<String, String>>();
			
			result.add(new Pair<String, String>(Chart.PROPERTY_SHOW_TITLE, "" + (chart.getTitle() != null)));
			if (chart.getTitle() != null) {
				result.add(new Pair<String, String>(Chart.PROPERTY_TITLE, chart.getTitle().getText()));
				result.add(new Pair<String, String>(Chart.PROPERTY_TITLE_FONT, "" + fontToString(chart.getTitle().getFont())));
				result.add(new Pair<String, String>(Chart.PROPERTY_TITLE_COLOR, "" + ((Color)chart.getTitle().getPaint()).getRGB()));
			}
			
			result.add(new Pair<String, String>(Chart.PROPERTY_BACKGROUND, "" + ((Color)chart.getBackgroundPaint()).getRGB()));
			result.add(new Pair<String, String>(Chart.PROPERTY_DRAW_ANTIALIASED, "" + chart.getAntiAlias()));

			result.add(new Pair<String, String>(Chart.PROPERTY_BORDER_VISIBLE, "" + chart.isBorderVisible()));
			if (chart.isBorderVisible()) {
				result.add(new Pair<String, String>(Chart.PROPERTY_BORDER, "" + strokeToString(chart.getBorderStroke())));
				result.add(new Pair<String, String>(Chart.PROPERTY_BORDER_COLOR, "" + ((Color)chart.getBorderPaint()).getRGB()));
			}

			result.add(new Pair<String, String>(Chart.PROPERTY_PLOT_BACKGROUND, "" + ((Color)chart.getPlot().getBackgroundPaint()).getRGB()));

			return result;
		}
		private static void setCommonChartProperties(Chart chart, JFreeChart jfreechart) {
			if (chart.getProperty(PROPERTY_SHOW_TITLE, Boolean.class, Boolean.TRUE)) {
				jfreechart.getTitle().setText(chart.getProperty(PROPERTY_TITLE, String.class));
				if (chart.getProperty(PROPERTY_TITLE_FONT, Font.class) != null) {
					jfreechart.getTitle().setFont(chart.getProperty(PROPERTY_TITLE_FONT, Font.class));
				}
				jfreechart.getTitle().setPaint(chart.getProperty(PROPERTY_TITLE_COLOR, Color.class));
			}
			jfreechart.setBackgroundPaint(chart.getProperty(PROPERTY_BACKGROUND, Color.class));
			jfreechart.setAntiAlias(chart.getProperty(PROPERTY_DRAW_ANTIALIASED, Boolean.class, Boolean.TRUE));
			
			jfreechart.setBorderVisible(chart.getProperty(PROPERTY_BORDER_VISIBLE, Boolean.class, Boolean.FALSE));
			if (jfreechart.isBorderVisible()) {
				jfreechart.setBorderStroke(chart.getProperty(PROPERTY_BORDER, Stroke.class));
				jfreechart.setBorderPaint(chart.getProperty(PROPERTY_BORDER_COLOR, Color.class));
			}
			
			final Plot plot = jfreechart.getPlot();
	        plot.setBackgroundPaint(chart.getProperty(PROPERTY_PLOT_BACKGROUND, Color.class));
	        
	        if (plot instanceof XYPlot) {
	        	((XYPlot)plot).setRangeGridlinesVisible(true);
	        	((XYPlot)plot).setDomainGridlinesVisible(true);
	        }
	        if (plot instanceof CategoryPlot) {
	        	((CategoryPlot)plot).setRangeGridlinesVisible(true);
	        	((CategoryPlot)plot).setDomainGridlinesVisible(true);
	        }

	        //LegendTitle legendtitle = new LegendTitle(plot);
			//legendtitle.setBackgroundPaint(Color.white);
			//legendtitle.setFrame(new BlockBorder());
			//legendtitle.setPosition(RectangleEdge.BOTTOM);
	        //jfreechart.addLegend(legendtitle);
		}
	}
	

	public class ScrollPaneWidthTrackingPanel extends JPanel implements Scrollable {
	    private static final long serialVersionUID = 1L;

	    public ScrollPaneWidthTrackingPanel(LayoutManager layoutManager) {
	        super(layoutManager);
	    }

	    public Dimension getPreferredScrollableViewportSize() {
	        return getPreferredSize();
	    }

	    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
	        return Math.max(visibleRect.height * 9 / 10, 1);
	    }

	    public boolean getScrollableTracksViewportHeight() {
	        return false;
	    }

	    public boolean getScrollableTracksViewportWidth() {
	        return true;
	    }

	    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
	        return Math.max(visibleRect.height / 10, 1);
	    }
	}

	public class ScrollPaneHeightTrackingPanel extends JPanel implements Scrollable {
	    private static final long serialVersionUID = 1L;

	    public ScrollPaneHeightTrackingPanel(LayoutManager layoutManager) {
	        super(layoutManager);
	    }

	    public Dimension getPreferredScrollableViewportSize() {
	        return getPreferredSize();
	    }

	    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
	        return Math.max(visibleRect.width * 9 / 10, 1);
	    }

	    public boolean getScrollableTracksViewportHeight() {
	        return true;
	    }

	    public boolean getScrollableTracksViewportWidth() {
	        return false;
	    }

	    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
	        return Math.max(visibleRect.width / 10, 1);
	    }
	}

	public interface ChartToolListener extends EventListener {
		void toolbarAction(String actionCommand);
	}

	public static enum ToolbarFunction {
		SAVE {
	        @Override
	        public AbstractButton createButton() {
	        	JButton res = new JButton(Icons.getInstance().getIconSave16());
	        	res.setToolTipText(SpringLocaleDelegate.getInstance().getMessage(
	        			"Chart.7","Diagramm speichern"));
	        	res.setActionCommand(name());
		        return res;
	        }
			@Override
			public JMenuItem createMenuItem() {
				JMenuItem res = new JMenuItem(SpringLocaleDelegate.getInstance().getMessage(
						"Chart.7","Diagramm speichern"), Icons.getInstance().getIconSave16());
				res.setActionCommand(name());
				return res;
			}
        },
		PRINT {
	        @Override
	        public AbstractButton createButton() {
	        	JButton res = new JButton(Icons.getInstance().getIconPrint16());
	        	res.setToolTipText(SpringLocaleDelegate.getInstance().getMessage(
	        			"Chart.1","Diagramm drucken"));
	        	res.setActionCommand(ChartPanel.PRINT_COMMAND);
		        return res;
	        }
			@Override
			public JMenuItem createMenuItem() {
				JMenuItem res = new JMenuItem(SpringLocaleDelegate.getInstance().getMessage(
						"Chart.1","Diagramm drucken"), Icons.getInstance().getIconPrint16());
				res.setActionCommand(ChartPanel.PRINT_COMMAND);
				return res;
			}
        },
		SHOW {
	        @Override
	        public AbstractButton createButton() {
	        	JButton res = new JButton(Icons.getInstance().getIconShowList());
	        	res.setToolTipText(SpringLocaleDelegate.getInstance().getMessage(
	        			"Chart.2","Diagrammdaten anzeigen"));
	        	res.setActionCommand(name());
		        return res;
	        }
			@Override
			public JMenuItem createMenuItem() {
				JMenuItem res = new JMenuItem(SpringLocaleDelegate.getInstance().getMessage(
						"Chart.2","Diagrammdaten anzeigen"), Icons.getInstance().getIconShowList());
				res.setActionCommand(name());
				return res;
			}
        };

		public abstract AbstractButton createButton();
		
		public abstract JMenuItem createMenuItem();

		public static ToolbarFunction fromCommandString(String actionCommand) {
			try {
				return ToolbarFunction.valueOf(actionCommand);
			}
			catch(Exception e) {
				LOG.info("fromCommandString failed on " + actionCommand, e);
				return null;
			}
		}
	}

	public static enum ToolbarFunctionState {
		ACTIVE(true, true),
        DISABLED(false, true),
        HIDDEN(false, false);

		private boolean isEnabled;
		private boolean isVisible;

		private ToolbarFunctionState(boolean isEnabled, boolean isVisible) {
	        this.isEnabled = isEnabled;
	        this.isVisible = isVisible;
        }

		public void set(AbstractButton a) {
			a.setEnabled(isEnabled);
			a.setVisible(isVisible);
		}
		
		public void set(JMenuItem mi) {
			mi.setEnabled(isEnabled);
			mi.setVisible(isVisible);
		}
	}

	private static final Color LAYER_BUSY_COLOR = new Color(128, 128, 128, 128);

	private JXLayer<JComponent>	layer;
	private AtomicInteger lockCount	= new AtomicInteger(0);

	private Map<String, AbstractButton> toolbarButtons;
	private List<String> toolbarOrder;
	private Map<String, JMenuItem> toolbarMenuItems;

	/**
	 * Can't be final because it must be set to null in close() to avoid memeory leaks. (tp)
	 */
	private JToolBar toolbar;

	/**
	 * Can't be final because it must be set to null in close() to avoid memeory leaks. (tp)
	 */
	private final JPanel contentPane = new JPanel(new BorderLayout());

	private final JComponent scrollPane;
	
	private ChartPanel panel;

	private final UID         entityName;
	private final UID         foreignKeyFieldToParent;
	
	private final Map<String, Object> properties = new HashMap<String, Object>();

	private final List<LookupListener>		lookupListener = new ArrayList<LookupListener>();

	private final List<FocusActionListener> lstFocusActionListener
		= new ArrayList<FocusActionListener>();

	private List<ChangeListener> lstchangelistener = new LinkedList<ChangeListener>();

	private List<ChartToolListener> listeners;

	private IPopupListener popupMenuListener;
	
	private SubForm subform;
	
	/**
	 * maps column names to columns
	 */
	private final Map<UID, Column> mpColumns = new LinkedHashMap<UID, Column>();
	private boolean closed = false;
	private boolean readonly = false;
	private final boolean bFromProperties;
	private final boolean bSearchable;
	
	/**
	 * §precondition entityName != null
	 * §postcondition this.getForeignKeyFieldToParent() == null
	 *  @param entityUID
	 * @param iToolBarOrientation see {@link JToolBar#setOrientation(int)}
	 * @param rootLayoutUID
	 */
	public Chart(UID entityUID, int iScrollPane, int iToolBarOrientation, final UID rootLayoutUID) {
		this(entityUID, iScrollPane, iToolBarOrientation, rootLayoutUID, null);
		
		assert this.getForeignKeyFieldToParent() == null;
	}
	/**
	 * §precondition entityName != null
	 * §postcondition this.getForeignKeyFieldToParent() == foreignKeyFieldToParent
	 *  @param entityName
	 * @param toolBarOrientation see {@link JToolBar#setOrientation(int)}
	 * @param rootLayoutUID
	 * @param foreignKeyFieldToParent Needs only be specified if not unique. @see #getForeignKeyFieldToParent()
	 */
	public Chart(UID entityName, int iScrollPane, int toolBarOrientation, final UID rootLayoutUID, UID foreignKeyFieldToParent) {
		this(entityName, iScrollPane, toolBarOrientation, rootLayoutUID, foreignKeyFieldToParent, false, false);
	}

	/**
	 * §precondition entityName != null
	 * §postcondition this.getForeignKeyFieldToParent() == foreignKeyFieldToParent
	 *  @param entityName
	 * @param toolBarOrientation see {@link JToolBar#setOrientation(int)}
	 * @param rootLayoutUID
	 * @param foreignKeyFieldToParent Needs only be specified if not unique. @see #getForeignKeyFieldToParent()
	 */
	public Chart(UID entityName, int iScrollPane, int toolBarOrientation, final UID rootLayoutUID, UID foreignKeyFieldToParent, boolean bFromProperties, boolean bSearchable) {
		super(new GridLayout(1, 1));

		this.bFromProperties = bFromProperties;
		this.bSearchable = bSearchable;
		
		this.subform = new SubForm(entityName, bSearchable, -1, rootLayoutUID, foreignKeyFieldToParent, bFromProperties) {
			private TableModelListener tblmdllistener;
			public void setupTableModelListener() {
				// set all columns visble.
				if (!(getSubformTable().getModel() instanceof CollectableTableModel<?,?>)) {
						return;
				}
				
				CollectableEntity colEntity = DefaultCollectableEntityProvider.getInstance().getCollectableEntity(subform.getEntityUID());
				List<CollectableEntityField> lstclctefColumns = new ArrayList<CollectableEntityField>();
				final MetaProvider mp = MetaProvider.getInstance();
				for (final UID sFieldName : colEntity.getFieldUIDs()) {
					if (null == mp.getEntityField(sFieldName).getForeignEntity()) {
						lstclctefColumns.add(colEntity.getEntityField(sFieldName));
					}
					/* @todo if (!sFieldName.equalsIgnoreCase("genericObject")) {
						lstclctefColumns.add(colEntity.getEntityField(sFieldName));
					}*/
				}
				((CollectableTableModel<?,?>) getSubformTable().getModel()).setColumns(lstclctefColumns);

				this.tblmdllistener = new TableModelListener() {
					private boolean enabled = Chart.this.isEnabled();
					@Override
		            public void tableChanged(TableModelEvent ev) {
						Chart.this.setEnabled((subform.getJTable().getModel() instanceof SearchConditionTableModelImpl) ? false : enabled);
						
						String type = getProperty(PROPERTY_TYPE, String.class);
						if (type != null) {
							JFreeChart chart = ChartFunction.valueOf(type).createChart(Chart.this);
							chart = replaceDynmicValuesinChart(Chart.this, chart); // replace all ${coulum} here.
							
							panel.setChart(chart);
							panel.repaint();
						}
					}
				};
				
				getSubformTable().getModel().addTableModelListener(this.tblmdllistener);
			}

			public void removeTableModelListener() {
				getSubformTable().getModel().removeTableModelListener(this.tblmdllistener);
				this.tblmdllistener = null;
			}
		};
		
		if (iScrollPane != -1) {
			this.scrollPane = new JScrollPane();
		} else {
			this.scrollPane = new JPanel(new BorderLayout());
		}
		
		this.toolbar = UIUtils.createNonFloatableToolBar(toolBarOrientation);

		this.listeners = new ArrayList<ChartToolListener>();

		if (entityName == null) {
			throw new NullArgumentException("entityName");
		}
		this.entityName = entityName;

		final List<DatasourceParameterVO> lstParams = ChartUtils.getParameterListForDataSource(entityName, DatasourceDelegate.getInstance());
		if (!lstParams.isEmpty() && !bSearchable && !bFromProperties) {
			JPanel pnl = new JPanel(new BorderLayout());
			ParameterPanel pnlParameters = new ParameterPanel(lstParams, null, new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					if (e != null && e.getSource() instanceof ParameterPanel) {
						try {
							Map<String, Object> mpParams = new HashMap<String, Object>();
							((ParameterPanel)e.getSource()).fillParameterMap(lstParams, mpParams);
							subform.setMapParams(mpParams);
						} catch (Exception e2) {
							//ignore.
						}
					}
				}
			});
			pnl.add(pnlParameters, BorderLayout.NORTH);
			
			JSplitPane splitPane = new JSplitPane(
					toolBarOrientation == -1 ? JSplitPane.VERTICAL_SPLIT : toolBarOrientation, pnl, scrollPane);
			contentPane.add(splitPane, BorderLayout.CENTER);
		} else {
			contentPane.add(scrollPane, BorderLayout.CENTER);
		}
		
		if (toolBarOrientation == -1) {
			this.toolbar.setVisible(false);
		} else {
			this.toolbar.setOrientation(toolBarOrientation);
		}
		this.foreignKeyFieldToParent = foreignKeyFieldToParent;
		
		layer = new JXLayer<JComponent>(contentPane, new TranslucentLockableUI());
		layer.setName("JXLayerGlasspane");
		add(layer);

		this.init(iScrollPane);

		toolbarButtons = new HashMap<String, AbstractButton>();
		toolbarMenuItems = new HashMap<String, JMenuItem>();
		toolbarOrder = new ArrayList<String>();
		for(ToolbarFunction func : ToolbarFunction.values()) {
			AbstractButton button = func.createButton();
			JMenuItem mi = func.createMenuItem();
			toolbarButtons.put(func.name(), button);
			toolbarMenuItems.put(func.name(), mi);
			toolbar.add(button);
			if (func.equals(ToolbarFunction.PRINT)) {
				button.addActionListener(panel);
				mi.addActionListener(panel);
			} else {
				button.addActionListener(this);
				mi.addActionListener(this);
			}
			toolbarOrder.add(func.name());
		}
		
		subform.setReadOnly(true);
		setEnabled(!bFromProperties && !bSearchable);
		
		addChartToolListener(new ChartToolListener() {
			
			@Override
			public void toolbarAction(String actionCommand) {
				if (actionCommand.equals(ToolbarFunction.SHOW.name())) {
					actionCommandShow();
				} else if (actionCommand.equals(ToolbarFunction.SAVE.name())) {
					actionCommandSave();
				}
			}
		});
		
		assert this.getForeignKeyFieldToParent() == foreignKeyFieldToParent;
	}
	
	private void actionCommandShow() {
		final JOptionPane optpn = new JOptionPane(subform, JOptionPane.PLAIN_MESSAGE, JOptionPane.CLOSED_OPTION);

		// perform the dialog:
		final JDialog dlg = optpn.createDialog(panel, "Chart-Daten anzeigen");
		dlg.setModal(true);
		dlg.setResizable(true);
		dlg.pack();
		dlg.setLocationRelativeTo(panel);
		dlg.setVisible(true);
	}
	private void actionCommandSave() {
		try {
			JFileChooser fileChooser = new JFileChooser();
			 // @todo i18n
	    	FileFilter filter = new FileNameExtensionFilter(
	    			SpringLocaleDelegate.getInstance().getMessage("filenameextensionfilter.1",
	    			"Bildformate") + " (*.svg, *.png, *.jpg, *.jpeg)", "svg", "png", "jpg", "jpeg");
	        fileChooser.addChoosableFileFilter(filter);

	        int option = fileChooser.showSaveDialog(this);
	        if (option == JFileChooser.APPROVE_OPTION) {
	            String filename = fileChooser.getSelectedFile().getPath();
	            if (fileChooser.getSelectedFile().getName().lastIndexOf(".") == -1) {
	               filename = filename + ".png";
	            }
	            
	            if (filename.toLowerCase().endsWith(".png")) {
		            ChartUtilities.saveChartAsPNG(new File(filename), getChartPanel().getChart(),
		                    getChartPanel().getWidth(), getChartPanel().getHeight());
	            } else if (filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".jpeg")) {
		            ChartUtilities.saveChartAsJPEG(new File(filename), getChartPanel().getChart(),
		            		getChartPanel().getWidth(), getChartPanel().getHeight());
	            } else if (filename.toLowerCase().endsWith(".svg")) {
	            	// Get a DOMImplementation and create an XML document
	                DOMImplementation domImpl =
	                    GenericDOMImplementation.getDOMImplementation();
	                Document document = domImpl.createDocument(null, "svg", null);

	                // Create an instance of the SVG Generator
	                SVGGraphics2D svgGenerator = new SVGGraphics2D(document);

	                // draw the chart in the SVG generator
	                JFreeChart chart = getChartPanel().getChart();
	                chart.draw(svgGenerator, getChartPanel().getBounds());

	                // Write svg file
	                OutputStream outputStream = new FileOutputStream(filename);
	                Writer out = new OutputStreamWriter(outputStream, "UTF-8");
	                svgGenerator.stream(out, true /* use css */);						
	                outputStream.flush();
	                outputStream.close();
	            }
	        }
		} catch (Exception e) {
			throw new NuclosFatalException(e.getMessage(), e);
		}
	}
	
	public SubForm getSubForm() {
		return this.subform;
	}
	
	@Override
	public final void close() {
		// Close is needed for avoiding memory leaks
		// If you want to change something here, please consult me (tp).  
		if (!closed) {
			LOG.debug("close(): " + this);
			
			// Partial fix for http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=7079260
			popupMenuListener = null;

			toolbar = null;
			
			lstchangelistener.clear();
			lstFocusActionListener.clear();
			listeners.clear();
			
			closed = true;
		}
	}
	
	public void addChartToolListener(ChartToolListener l) {
		listeners.add(l);
	}

	public void removeChartToolListener(ChartToolListener l) {
		listeners.remove(l);
	}

	public void removeAllChartToolListeners() {
		listeners.clear();
	}
	
	public void actionPerformed(String actionCommand) {
		if(actionCommand != null) {
			for(ChartToolListener l : new ArrayList<ChartToolListener>(listeners)) {
				l.toolbarAction(actionCommand);
			}
		}
	}

	@Override
    public void actionPerformed(ActionEvent e) {
		String actionCommand = StringUtils.nullIfEmpty(e.getActionCommand());
		actionPerformed(actionCommand);
	}

	public void setToolbarFunctionState(ToolbarFunction func, ToolbarFunctionState state) {
		setToolbarFunctionState(func.name(), state);
	}

	public void setToolbarFunctionState(String toolbarActionCommand, ToolbarFunctionState state) {
		final AbstractButton button = toolbarButtons.get(toolbarActionCommand);
		if (button != null) {
			state.set(button);
		}
		final JMenuItem mi = toolbarMenuItems.get(toolbarActionCommand);
		if (mi != null) {
			state.set(mi);
		}
	}

	public void addToolbarFunction(String actionCommand, AbstractButton button, JMenuItem mi, int pos) {
		if(toolbarButtons.containsKey(actionCommand)) {
			toolbar.remove(toolbarButtons.get(actionCommand));
		}
		toolbarButtons.put(actionCommand, button);
		button.setActionCommand(actionCommand);
		button.addActionListener(this);
		toolbar.add(button, pos);
		toolbar.validate();
		
		if (toolbarMenuItems.containsKey(actionCommand)) {
			toolbarOrder.remove(actionCommand);
		}
		toolbarMenuItems.put(actionCommand, mi);
		mi.setActionCommand(actionCommand);
		mi.addActionListener(this);
		toolbarOrder.add(pos, actionCommand);
	}


	//--- needed by the wysiwyg editor only -----------------------------------
	public void addToolbarButtonMouseListener(MouseListener l) {
		for(AbstractButton b : toolbarButtons.values()) {
			b.addMouseListener(l);
		}
	}

	public void removeToolbarButtonMouseListener(MouseListener l) {
		for(AbstractButton b : toolbarButtons.values()) {
			b.removeMouseListener(l);
		}
	}
	
	public ChartPanel getChartPanel() {
		return panel;
	}

	public JToolBar getToolbar() {
		return toolbar;
	}

	public int getToolbarOrientation() {
		return toolbar.getOrientation();
	}

	public Rectangle getToolbarBounds() {
		return toolbar.getBounds();
	}

	public Set<String> getToolbarFunctions() {
		return toolbarButtons.keySet();
	}

	public AbstractButton getToolbarButton(String function) {
		return toolbarButtons.get(function);
	}
	//--- end needed by the wysiwyg editor only --------------------------------


	// class TranslucentLockableUI --->
	private static class TranslucentLockableUI extends LockableUI {
		@Override
		protected void paintLayer(Graphics2D g2, JXLayer<? extends JComponent> l) {
			super.paintLayer(g2, l);
			if(isLocked()) {
				g2.setColor(LAYER_BUSY_COLOR);
				g2.fillRect(0, 0, l.getWidth(), l.getHeight());
			}
		}
	} // class TranslucentLockableUI

	public void setLockedLayer() {
		if(lockCount.incrementAndGet() == 1)
			if(layer != null && !((LockableUI) layer.getUI()).isLocked())
				((LockableUI) layer.getUI()).setLocked(true);
	}

	public void restoreUnLockedLayer() {
		if(lockCount.decrementAndGet() == 0) {
			final boolean bDoLock = layer != null && ((LockableUI) layer.getUI()).isLocked(); 
			if(!bDoLock) {
				((LockableUI) layer.getUI()).setLocked(false);
			}
		}
	}

	public void forceUnlockFrame() {
		lockCount.set(0);
		((LockableUI) layer.getUI()).setLocked(false);
	}

	private void init(int iScrollPane) {
		contentPane.add(toolbar,
			toolbar.getOrientation() == JToolBar.HORIZONTAL
			? BorderLayout.NORTH
			: BorderLayout.WEST);

		// Configure table
		JFreeChart chart = new JFreeChart(new Plot() {
			
			@Override
			public String getPlotType() {
				return null;
			}
			
			@Override
			public void draw(Graphics2D arg0, Rectangle2D arg1, Point2D arg2,
					PlotState arg3, PlotRenderingInfo arg4) {
			}
		});

		panel = new ChartPanel(chart, false, false, true, false, true);
		if (bFromProperties) {
			panel.setPopupMenu(null);
		}
		
		if (iScrollPane == -1) {
			scrollPane.add(panel, BorderLayout.CENTER);
		} else {
			((JScrollPane)scrollPane).getViewport().setBackground(panel.getBackground());

			JLabel labCorner = new JLabel();
			labCorner.setEnabled(false);
			labCorner.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.GRAY));
			labCorner.setBackground(Color.LIGHT_GRAY);
			((JScrollPane)scrollPane).setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, labCorner);
			
			//both
			if (iScrollPane != 2) {
				// horizontal
				if (iScrollPane == 0) { 
					JPanel pnl = new ScrollPaneHeightTrackingPanel(new BorderLayout());
					pnl.add(panel, BorderLayout.CENTER);
					((JScrollPane)scrollPane).getViewport().setView(pnl);
					((JScrollPane)scrollPane).setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
					((JScrollPane)scrollPane).setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				}
				// vertical
				else if (iScrollPane == 1) {
					JPanel pnl = new ScrollPaneWidthTrackingPanel(new BorderLayout());
					pnl.add(panel, BorderLayout.CENTER);
					((JScrollPane)scrollPane).getViewport().setView(pnl);
					((JScrollPane)scrollPane).setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
					((JScrollPane)scrollPane).setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				}
				// none
				else if (iScrollPane == -1) { 
					((JScrollPane)scrollPane).setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
					((JScrollPane)scrollPane).setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				} 
			} else {
				((JScrollPane)scrollPane).getViewport().setView(panel);
			}
		}
	}
	
	/**
	 * do not store items permanent!
	 * @param result
	 */
	public void addToolbarMenuItems(List<JComponent> result) {
		for (String actionCommand : toolbarOrder) {
			result.add(toolbarMenuItems.get(actionCommand));
		}
	}

	/**
	 * §postcondition result != null
	 * 
	 * @return the name of this chart's entity.
	 */
	public UID getEntityName() {
		return this.entityName;
	}

	/**
	 * @return the foreign key field that references the parent object. May be null for convenience.
	 */
	public final UID getForeignKeyFieldToParent() {
		return this.foreignKeyFieldToParent;
	}
	
	@Override
	public final void setEnabled(boolean enabled) {
		enabled = enabled && !readonly;
		super.setEnabled(enabled);
		
		setToolbarFunctionState(ToolbarFunction.SAVE,
				((bFromProperties || bSearchable) ? false : enabled) ? ToolbarFunctionState.ACTIVE : ToolbarFunctionState.DISABLED);
		setToolbarFunctionState(ToolbarFunction.PRINT,
				((bFromProperties || bSearchable) ? false : enabled) ? ToolbarFunctionState.ACTIVE : ToolbarFunctionState.DISABLED);
		setToolbarFunctionState(ToolbarFunction.SHOW,
				((bFromProperties || bSearchable) ? false : enabled) ? ToolbarFunctionState.ACTIVE : ToolbarFunctionState.DISABLED);
	}
	
	public void setReadOnly(boolean readonly) {
		this.readonly = readonly;
	}
	
	public boolean isReadOnly() {
		return readonly;
	}
	
	/**
	 * @param listener
	 */
	public synchronized void addChangeListener(ChangeListener listener) {
		this.lstchangelistener.add(listener);
	}

	/**
	 * @param listener
	 */
	public synchronized void removeChangeListener(ChangeListener listener) {
		this.lstchangelistener.remove(listener);
	}

	/**
	 * fires a <code>ChangeEvent</code> whenever the model of this <code>Chart</code> changes.
	 */
	public synchronized void fireStateChanged() {
		if(layer == null || (layer != null && !((LockableUI) layer.getUI()).isLocked())){
			final ChangeEvent ev = new ChangeEvent(this);
			for (ChangeListener changelistener : lstchangelistener) {
				changelistener.stateChanged(ev);
			}
		}
	}

	/**
	 * @param column
	 */
	public void addColumn(Column column) {
		this.mpColumns.put(column.getUID(), column);
	}

	public Column getColumn(UID uid) {
		return this.mpColumns.get(uid);
	}

	public String getColumnLabel(UID uid) {
		final Column column = this.getColumn(uid);
		return (column != null) ? column.getLabel() : null;
	}

	public IPopupListener getPopupMenuListener() {
		return popupMenuListener;
	}

	public void setPopupMenuListener(IPopupListener popupMenuListener) {
		this.popupMenuListener = popupMenuListener;
	}
	
	/**
	 * @param sName
	 * @return the value of the dynamic property with the given name, if any.
	 */
	public <T> T getProperty(String sName, Class<T> clazz) {
		return getProperty(sName, clazz, null);
	}
	/**
	 * @param sName
	 * @return the value of the dynamic property with the given name, if any.
	 */
	public <T> T getProperty(String sName, Class<T> clazz, T defaultValue) {
		try {
			if (clazz.equals(Color.class)) {
				String p = getProperty(sName, String.class, "");
				if (StringUtils.isNullOrEmpty(p)) {
					return (T)defaultValue;
				}
				return (T)colorFromString(p);
			} else if (clazz.equals(Font.class)) {
				String p = getProperty(sName, String.class, "");
				if (StringUtils.isNullOrEmpty(p)) {
					return (T)defaultValue;
				}
				return (T)fontFromString(p);
			} else if (clazz.equals(Stroke.class)) {
				String p = getProperty(sName, String.class, "");
				if (StringUtils.isNullOrEmpty(p)) {
					return (T)defaultValue;
				}
				return (T)strokeFromString(p);
			} else if (clazz.equals(Integer.class)) {
				String p = getProperty(sName, String.class, "");
				if (StringUtils.isNullOrEmpty(p)) {
					return (T)defaultValue;
				}
				return (T)Integer.valueOf(p);
			} else if (clazz.equals(Double.class)) {
				String p = getProperty(sName, String.class, "");
				if (StringUtils.isNullOrEmpty(p)) {
					return (T)defaultValue;
				}
				return (T)new Double(p);
			} else if (clazz.equals(Boolean.class)) {
				String p = getProperty(sName, String.class, "");
				if (StringUtils.isNullOrEmpty(p)) {
					return (T)defaultValue;
				}
				return (T)Boolean.valueOf(p);
			} else if (clazz.equals(UID.class)) {
				String p = getProperty(sName, String.class, "");
				if (StringUtils.isNullOrEmpty(p)) {
					return (T)defaultValue;
				}
				return (T)uidFromString(p);
			}
			if (properties.get(sName) == null) {
				return (T)defaultValue;
			}
			return (T)(properties.get(sName) != null && properties.get(sName).equals("null") ? null : properties.get(sName));	
		} catch (Exception e) {
			return (T)defaultValue;
		}
	}
	
	private static Font fontFromString(String font) {
		return Font.decode(font);
	}
	static String fontToString(Font font) {
		String  strStyle;

        if (font.isBold()) {
            strStyle = font.isItalic() ? "bolditalic" : "bold";
        } else {
            strStyle = font.isItalic() ? "italic" : "plain";
        }

        return font.getName() + "-" + strStyle + "-" + font.getSize();
    }
	private static UID uidFromString(String uid) {
		return UID.parseUID(uid);
	}
	private static Color colorFromString(String color) {
		return new Color(Integer.parseInt(color));
	}
	private static Stroke strokeFromString(String stroke) {
		Stroke result = null;
		String[] str = stroke.split("-"); 
		if (str.length == 6) {
			result = new BasicStroke(Float.parseFloat(str[0]),
					Integer.parseInt(str[1]),Integer.parseInt(str[2]),
					Float.parseFloat(str[3]),
					stringToFloatArray(str[4], ","),
					Float.parseFloat(str[5]));
		}
		return result;
	}
	private static String strokeToString(Stroke stroke) {
		if (stroke instanceof BasicStroke) {
			return ((BasicStroke)stroke).getLineWidth() + "-" + 
					((BasicStroke)stroke).getEndCap() + "-" + 
					((BasicStroke)stroke).getLineJoin() + "-" + 
					((BasicStroke)stroke).getMiterLimit() + "-" + 
					floatArrayToString(((BasicStroke)stroke).getDashArray(), ",") + "-" +
					((BasicStroke)stroke).getDashPhase();
		}
		return "";
	}
	private static float[] stringToFloatArray(String arg, String separator) {
		String[] floats = arg.split(separator);
		float[] result = new float[floats.length];
		for (int i = 0; i < floats.length; i++) {
			result[i] = Float.parseFloat(floats[i]);
		}
	    return result;
	}
	private static String floatArrayToString(float[] a, String separator) {
	    StringBuffer result = new StringBuffer();
	    if (a.length > 0) {
	        result.append(a[0]);
	        for (int i=1; i<a.length; i++) {
	            result.append(separator);
	            result.append(a[i]);
	        }
	    }
	    return result.toString();
	}

	/**
	 * sets the dynamic property with the given name to the given value.
	 * Properties can be used to customize an individual <code>CollectableComponent</code>.
	 * 
	 * §postcondition LangUtils.equals(this.getProperty(sName), oValue)
	 */
	public void setProperty(String sName, Object oValue) {
		properties.put(sName, oValue);
	}

	/**
	 * §postcondition result != null
	 * 
	 * @return the Map of properties for this <code>CollectableComponent</code>.
	 */
	public Map<String, Object> getProperties() {
		return Collections.unmodifiableMap(properties);
	}
	
	/**
	 * §postcondition result != null
	 * 
	 * @return the name of the foreign key field referencing the parent entity
	 */
	public final UID getForeignKeyFieldName(String sParentEntityName, CollectableEntity clcte) {

		// Use the field referencing the parent entity from the chart, if any:
		UID result = this.getForeignKeyFieldToParent();

		if (result == null) {
			// Default: Find the field referencing the parent entity from the meta data.
			// If more than one field applies, throw an exception:
			for (UID sFieldName : clcte.getFieldUIDs()) {
				if (sParentEntityName.equals(clcte.getEntityField(sFieldName).getReferencedEntityUID())) {
					if (result == null) {
						// this is the foreign key field:
						result = sFieldName;
					}
					else {
						final String sMessage = SpringLocaleDelegate.getInstance().getMessage(
								"Chart.4","Das Diagramm f\u00fcr die Entit\u00e4t \"{0}\" enth\u00e4lt mehr als ein Fremdschl\u00fcsselfeld, das die \u00fcbergeordnete Entit\u00e4t \"{1}\" referenziert:\n\t{2}\n\t{3}\nBitte geben Sie das Feld im Layout explizit an.", clcte.getUID(), sParentEntityName, result, sFieldName);
						throw new CommonFatalException(sMessage);
					}
				}
			}
		}

		if (result == null) {
			throw new CommonFatalException(SpringLocaleDelegate.getInstance().getMessage(
					"Chart.3","Das Diagramm f\u00fcr die Entit\u00e4t \"{0}\" enth\u00e4lt kein Fremdschl\u00fcsselfeld, das die \u00fcbergeordnete Entit\u00e4t \"{1}\" referenziert.\nBitte geben Sie das Feld im Layout explizit an.", clcte.getUID(), sParentEntityName));
		}
		assert result != null;
		return result;
	}

	/**
     * @param lookupListener the LookupListener to remove
     */
    public void removeLookupListener(LookupListener lookupListener) {
	    this.lookupListener.remove(lookupListener);
    }

	/**
     * @param lookupListener the LookupListener to add
     */
    public void addLookupListener(LookupListener lookupListener) {
	    this.lookupListener.add(lookupListener);
    }

    public void addFocusActionListener(FocusActionListener fal) {
    	lstFocusActionListener.add(fal);
    }

    public void removeFocusActionListener(FocusActionListener fal) {
    	lstFocusActionListener.remove(fal);
    }

    public List<FocusActionListener> getFocusActionLister() {
    	return lstFocusActionListener;
    }
	/**
	 * A column in a <code>Chart</code>.
	 */
	public static class Column {
		private final UID uid;
		private String sLabel;
		
		private CollectableFieldsProvider valuelistprovider;

		public Column(UID uid) {
			this(uid, null);
		}

		public Column(UID uid, String sLabel) {
			this.uid = uid;
			this.sLabel = sLabel;
		}

		public UID getUID() {
			return this.uid;
		}

		public String getLabel() {
			return this.sLabel;
		}

		public void setLabel(String label) {
			this.sLabel = label;
		}

		@Override
		public boolean equals(Object oValue) {
			if (this == oValue) {
				return true;
			}
			if (!(oValue instanceof Column)) {
				return false;
			}
			return LangUtils.equal(getUID(), ((Column) oValue).getUID());
		}

		@Override
		public int hashCode() {
			return LangUtils.hashCode(getUID());
		}

		public void setValueListProvider(CollectableFieldsProvider valuelistprovider) {
			this.valuelistprovider = valuelistprovider;
		}

		public CollectableFieldsProvider getValueListProvider() {
			return this.valuelistprovider;
		}
	}	// inner class Column
	
	private JFreeChart replaceDynmicValuesinChart(Chart chart, JFreeChart jfreechart) {
		try {
			final JTable tbl = chart.getSubForm().getJTable();
			if (tbl.getRowCount() <= 0)
				return jfreechart;
			
			XStream xstream = new XStream();
			xstream.autodetectAnnotations(true);
			
			String xml = xstream.toXML(jfreechart);
			
			xml = RigidUtils.replaceParameters(RigidUtils.PARAM_PATTERN_$, xml, new Transformer<String, String>() {
				@Override
				public String transform(String i) {
					final Enumeration<TableColumn> enumeration = tbl.getColumnModel().getColumns();
					while (enumeration.hasMoreElements()) {
						final TableColumn column = enumeration.nextElement();
						if (LangUtils.equal(column.getHeaderValue(), i)) {
							int col = tbl.getColumnModel().getColumnIndex(column.getIdentifier());
							Object value = tbl.getValueAt(0, col);
							return value == null ? "" : value.toString();
						}	
					}
					return "";
				}					
			});

			jfreechart = (JFreeChart)xstream.fromXML(xml);
		} catch (Exception e) {
			LOG.warn("could not get chart from stream. " + e.getMessage());
		}
		return jfreechart;
	}
}	// class Chart
