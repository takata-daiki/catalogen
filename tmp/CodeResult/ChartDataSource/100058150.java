/**
 * Copyright 2014  XCL-Charts
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 	
 * @Project XCL-Charts 
 * @Description Android图表基类库
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version v0.1
 */

package org.xclcharts.chart;

import java.util.List;

import org.xclcharts.common.DrawHelper;
import org.xclcharts.renderer.bar.Bar;
import org.xclcharts.renderer.bar.Bar3D;

import android.graphics.Canvas;
import android.util.Log;

/**
 * @ClassName Bar3DChart
 * @Description  3D柱形图的基类,包含横向和竖向3D柱形图
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *  
 */

public class BarChart3D extends BarChart{
	
	private static final String TAG = "BarChart3D";
	
	//3D柱形绘制
	private Bar3D mBar3D  = new Bar3D();

	public BarChart3D()
	{
		super();	
		plotKey.showKeyLabels();
	}
	
	/**
	 * 设置坐标系底座厚度 
	 * @param thickness 底座厚度 
	 */
	public void setAxis3DBaseThickness(int thickness)
	{
		mBar3D.setAxis3DBaseThickness(thickness);
	}
	
	/**
	 * 返回坐标系底座厚度 
	 * @return 底座厚度 
	 */
	public double getAxis3DBaseThickness()
	{
		return mBar3D.getAxis3DBaseThickness();
	}
	
	/**
	 * 设置柱形3D厚度
	 * @param thickness 厚度
	 */
	public void setBarThickness(int thickness)
	{
		mBar3D.setThickness(thickness);
	}
	
	/**
	 * 返回柱形3D厚度
	 * @return 厚度
	 */
	public double getBarThickness()
	{
		return  mBar3D.getThickness();
	}
	/**
	 * 设置3D偏转角度
	 * @param angle 角度
	 */
	public void setBarAngle(int angle)
	{
		mBar3D.setAngle(angle);
	}
	/**
	 * 返回3D偏转角度
	 * @return 角度
	 */
	public int getBarAngle()
	{
		return mBar3D.getAngle();
	}
	
	/**
	 * 设透明度
	 * @param alpha 透明度
	 */
	public void setBarAlpha(int alpha)
	{
		mBar3D.setAlpha(alpha);
	}
	
	/**
	 *  坐标基座颜色
	 * @param color 颜色
	 */
	public void setAxis3DBaseColor(int color)
	{
		mBar3D.setAxis3DBaseColor(color);
	}	
	
	@Override
	public Bar getBar()
	{
		return mBar3D;
	}
	
	/**
	 * 3D时，隐藏旧那个刻度线标识
	 */
	protected void defaultAxisSetting()
	{
		super.defaultAxisSetting();
		try{
			switch (getChartDirection()) {
				case HORIZONTAL: {				
					categoryAxis.setTickMarksVisible(false);		
					break;
				}
				case VERTICAL: {					
					categoryAxis.setTickMarksVisible(false);				
					break;
				}
			}
		}catch(Exception ex){
			Log.e(TAG, ex.toString());
		}
	}
	
	
	@Override
	protected void renderHorizontalBarLabelAxis(Canvas canvas) {
		// Y 轴
		// 分类横向间距高度
		float YSteps = (float)(getAxisScreenHeight()
				/ (this.categoryAxis.getDataSet().size() + 1));
		float currentY = 0.0f;
		for (int i = 0; i < categoryAxis.getDataSet().size(); i++) {
			// 依初超始Y坐标与分类间距算出当前刻度的Y坐标
			currentY = plotArea.getBottom() - (i + 1) * YSteps;
			// 横的grid线
			plotGrid.renderGridLinesHorizontal(canvas, plotArea.getLeft(),
					currentY, plotArea.getRight(), currentY);
							
			// 分类
			float labelX = (float) (plotArea.getLeft() - mBar3D.getOffsetX() * 2); 
			this.categoryAxis.renderAxisHorizontalTick(canvas, labelX,
					currentY, categoryAxis.getDataSet().get(i));
		}
	}
	
	
	@Override
	protected void renderHorizontalBar(Canvas canvas)
	{		
		renderHorizontalBarDataAxis(canvas);
		 
		//x轴 线 [要向里突]
		 dataAxis.renderAxis(canvas,plotArea.getLeft(), plotArea.getBottom(),
				 			 plotArea.getRight(),  plotArea.getBottom());	
		 //Y 轴           
		 renderHorizontalBarLabelAxis(canvas);
			
			//Y轴线
		 mBar3D.render3DYAxis(plotArea.getLeft(), plotArea.getTop(), 
							 plotArea.getRight(), plotArea.getBottom(),canvas);
			
			//得到Y 轴分类横向间距高度
			 float YSteps = getHorizontalYSteps();
	
			//得到数据源
			List<BarData> chartDataSource = this.getDataSource(); 
			//依柱形宽度，多柱形间的偏移值 与当前数据集的总数据个数得到当前分类柱形要占的高度	
			int barNumber = chartDataSource.size();
			int currNumber = 0;			
			
			List<Integer> ret = mBar3D.getBarHeightAndMargin(YSteps, barNumber);	
			int barHeight = ret.get(0);
			int barInnerMargin = ret.get(1);			
			int labelBarUseHeight = barNumber * barHeight + (barNumber - 1) * barInnerMargin;	
			
			float scrWidth = plotArea.getWidth();
			float valueWidth = (float) dataAxis.getAxisRange();
			
			for(int i=0;i<barNumber;i++)
			{					    
				//得到分类对应的值数据集
				BarData bd = chartDataSource.get(i) ; 
				List<Double> barValues = bd.getDataSet(); 
				List<Integer> barDataColor = bd.getDataColor();	
				//设置成对应的颜色	
				int barDefualtColor = bd.getColor();
				mBar3D.getBarPaint().setColor(barDefualtColor);
				
									
			    //画同分类下的所有柱形
				for (int j = 0; j < barValues.size(); j++) {
					Double bv = barValues.get(j);					
					setBarDataColor(mBar3D.getBarPaint(),barDataColor,j,barDefualtColor);
					
					float drawBarButtomY = plotArea.getBottom() - (j+1) * YSteps + labelBarUseHeight / 2;							
					drawBarButtomY = drawBarButtomY - (barHeight + barInnerMargin ) * currNumber;					
																				
                	//参数值与最大值的比例  照搬到 y轴高度与矩形高度的比例上来
                	float valuePostion = (float) ( 
                			scrWidth * ( (bv - dataAxis.getAxisMin() ) / valueWidth)) ;               	
                            	        
                	//画出柱形                	 	            
	                mBar3D.renderHorizontal3DBar(plotArea.getLeft(), 
	                							drawBarButtomY - barHeight, 
						                		(float) (plotArea.getLeft()  +  valuePostion), 
						                		drawBarButtomY, 
	                							mBar3D.getBarPaint().getColor(), canvas);
	                	             	
                	                               	
                	//在柱形的顶端显示上柱形的当前值	                
	                mBar3D.renderBarItemLabel(getFormatterItemLabel(bv),
	                		 (float) (plotArea.getLeft() + valuePostion)  , 
	                		 (float) (drawBarButtomY - barHeight/2), canvas);
                }
				currNumber ++;
			}	
			//画Key说明
			//renderDataKey(canvas);
			plotKey.renderBarKey(canvas, this.getDataSource());
	}
	
	
	@Override
	protected void renderVerticalBarCategoryAxis(Canvas canvas) {
		// 分类轴(X 轴)
		float currentX = plotArea.getLeft();

		// 得到分类轴数据集
		List<String> dataSet = categoryAxis.getDataSet();

		// 依传入的分类个数与轴总宽度算出要画的分类间距数是多少
		// 总宽度 / 分类个数 = 间距长度
		float XSteps = (getAxisScreenWidth() / (dataSet.size() + 1)); //Math.ceil
		
		//3D 偏移值		
	    double baseTickness = mBar3D.getAxis3DBaseThickness();
	    double baseAngle = mBar3D.getAngle();	
		double baseOffsetX = mBar3D.getOffsetX(baseTickness,baseAngle);
		double baseOffsetY = mBar3D.getOffsetY(baseTickness,baseAngle);	
		
		double labelHeight = DrawHelper.getInstance().getPaintFontHeight(
								categoryAxis.getAxisTickLabelPaint());
		
		for (int i = 0; i < dataSet.size(); i++) {
			// 依初超始X坐标与分类间距算出当前刻度的X坐标
			currentX = (plotArea.getLeft() + (i + 1) * XSteps); //Math.round

			// 绘制横向网格线
			if (plotGrid.isShowVerticalLines()) {
				canvas.drawLine(currentX, plotArea.getBottom(),
						currentX, plotArea.getTop(),
						this.plotGrid.getVerticalLinePaint());
			}
			// 画上分类/刻度线
			float currentY = (float) (plotArea.getBottom() + baseOffsetY + baseTickness+labelHeight);
			currentX = (float) (currentX - baseOffsetX);
			categoryAxis.renderAxisVerticalTick(canvas, currentX,currentY, dataSet.get(i));
		}
	}
		
	
	@Override
	protected void renderVerticalBar(Canvas canvas)
	{		
		renderVerticalBarDataAxis(canvas);
		renderVerticalBarCategoryAxis(canvas);
		
		//分类轴(X 轴) 且在这画柱形    
		 float currentX = 0.0f;
		 float initX = currentX = plotArea.getLeft();
				
			 		
		 //得到分类轴数据集
		List<String> dataSet =  categoryAxis.getDataSet();
				
		// 依传入的分类个数与轴总宽度算出要画的分类间距数是多少
		// 总宽度 / 分类个数 = 间距长度	
		float XSteps = ( plotArea.getWidth()/ (dataSet.size() + 1 )); //(int) Math.ceil				 			 
	 	//X轴 线
		mBar3D.render3DXAxis(plotArea.getLeft(), plotArea.getBottom(),
							 plotArea.getRight(), plotArea.getBottom(), 
							 canvas);
	
		//得到数据源
		List<BarData> chartDataSource = this.getDataSource();
		int barNumber = chartDataSource.size();
		int currNumber = 0;			
		
		List<Integer> ret = mBar3D.getBarWidthAndMargin(XSteps, barNumber);
		int barWidth = ret.get(0);
		int barInnerMargin = ret.get(1);
		int labelBarUseWidth = barNumber * barWidth + (barNumber - 1) * barInnerMargin;		
	
		 
		//开始处 X 轴 即分类轴                  
		for(int i=0;i<barNumber;i++)
		{
		    //依初超始X坐标与分类间距算出当前刻度的X坐标
			currentX = initX + (i+1) * XSteps; 
			
			//得到分类对应的值数据集				
			BarData bd = chartDataSource.get(i);
			List<Double> barValues = bd.getDataSet();
			List<Integer> barDataColor = bd.getDataColor();	
			//设成对应的颜色
			int barDefualtColor = bd.getColor();
			mBar3D.getBarPaint().setColor(barDefualtColor);
					 
		   //画出分类下的所有柱形
			 for (int j = 0; j < barValues.size(); j++) {
			   Double bv = barValues.get(j);
			   setBarDataColor(mBar3D.getBarPaint(),barDataColor,j,barDefualtColor);
			   
				//参数值与最大值的比例  照搬到 y轴高度与矩形高度的比例上来					
				float valuePostion = (float)( plotArea.getHeight() * 
											( (bv - dataAxis.getAxisMin() ) / dataAxis.getAxisRange())) ;              																
				float drawBarStartX = initX + (j + 1) * XSteps - labelBarUseWidth / 2;
				//计算同分类多柱 形时，新柱形的起始X坐标
				drawBarStartX = drawBarStartX + (barWidth + barInnerMargin ) * currNumber;
				//计算同分类多柱 形时，新柱形的结束X坐标
				float drawBarEndX = drawBarStartX + barWidth;	  					
				
				//画出柱形      
				mBar3D.renderVertical3DBar(drawBarStartX, 
           								(float)(plotArea.getBottom()  -  valuePostion) ,
				               			drawBarEndX, 
				               			plotArea.getBottom(),
				               			mBar3D.getBarPaint().getColor(), canvas);
        
			
           		//在柱形的顶端显示上柱形的当前值
           		mBar3D.renderBarItemLabel(getFormatterItemLabel(bv),
			                		 (float)(drawBarStartX + barWidth/2) ,	
			                		 (float)(plotArea.getBottom()  -  valuePostion),  
			                		 canvas);
           }	
			currNumber ++;				
		}
	 
		//绘制分类各柱形集的说明描述
		//renderDataKey(canvas);
		plotKey.renderBarKey(canvas, this.getDataSource());
	}
	
	
}
