package com.jeasonzhao.report.model.chart;

public class ChartTypes extends com.jeasonzhao.commons.basic.IntegerPair
{
    private static final long serialVersionUID = 1L;
    //Single Dimension
    public static final ChartTypes BarChart = new ChartTypes(1,"bar");
    public static final ChartTypes ColumnChart = new ChartTypes(3,"column,col");
    public static final ChartTypes LineChart = new ChartTypes(2,"line");
    public static final ChartTypes PieChart = new ChartTypes(4,"pie");
    public static final ChartTypes AreaChart = new ChartTypes(5,"area");
    public static final ChartTypes DoughnutChart = new ChartTypes(6,"doughnut");

    public static final ChartTypes StackedColumnChart = new ChartTypes(101,"StackedColumn");
    public static final ChartTypes StackedColumn100Chart = new ChartTypes(102,"100%StackedColumn,StackedColumn100");
    public static final ChartTypes StackedBarChart = new ChartTypes(103,"StackedBar");
    public static final ChartTypes StackedBar100Chart = new ChartTypes(104,"100%StackedBar,StackedBar100");
    public static final ChartTypes StackedAreaChart = new ChartTypes(105,"StackedArea");
    public static final ChartTypes StackedArea100Chart = new ChartTypes(106,"100%StackedArea,StackedArea100");

    public static final ChartTypes BubbleChart = new ChartTypes(1001,"Bubble");
    public static final ChartTypes PointChart = new ChartTypes(1002,"Point");
    public static final ChartTypes FunnelChart = new ChartTypes(1003,"Funnel,StreamLineFunnel");
    public static final ChartTypes Funnel100Chart = new ChartTypes(1004,"100%Funnel,Funnel100,SectionFunnel");
    public static final ChartTypes CandleStickChart = new ChartTypes(1005,"CandleStick");
    public static final ChartTypes StockChart = new ChartTypes(1006,"Stock");

    public ChartTypes(int n,String alias)
    {
        super(n,null,alias);
    }

    public static ChartTypes fromName(String str)
    {
        return(ChartTypes) com.jeasonzhao.commons.basic.IntegerPair.findConstant(ChartTypes.class,str,BarChart);
    }
}
