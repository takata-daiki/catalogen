
class QwtLegend__ extends QwtLegend {
    private java.util.Map<QwtLegendItemManager,com.trolltech.qt.gui.QWidget> __rcWidgetsForItemManager = new java.util.HashMap<QwtLegendItemManager,com.trolltech.qt.gui.QWidget>();
}// class

class QwtPlot__ extends QwtPlot {
    public final void insertLegend(QwtLegend legend) {
        insertLegend(legend, QwtPlot.LegendPosition.RightLegend, -1.0);
    }
    public final void insertLegend(QwtLegend legend, QwtPlot.LegendPosition pos) {
        insertLegend(legend, pos, -1.0);
    }
    public final void insertLegend(QwtLegend legend, QwtPlot.LegendPosition pos, double ratio) {
        com.trolltech.qt.GeneratorUtilities.threadCheck(this);
        if (nativeId() == 0)
            throw new QNoNativeResourcesException("Function call on incomplete object of type: " +getClass().getName());
        if (legend != null && pos != QwtPlot.LegendPosition.ExternalLegend) {
            legend.disableGarbageCollection();
        }
        final QwtLegend oldLegend = __qt_legend(nativeId());
        __qt_insertLegend_QwtLegend_LegendPosition_double(nativeId(), legend == null ? 0 : legend.nativeId(), pos.value(), ratio);
        if (oldLegend != null) {
            oldLegend.reenableGarbageCollection();
        }
    }
    @QtBlockedSlot
    native void __qt_insertLegend_QwtLegend_LegendPosition_double(long __this__nativeId, long legend, int pos, double ratio);

    @QtBlockedSlot
    public final QwtScaleDiv axisScaleDiv(int axisId) {
        return QwtScaleDiv.fromNativePointer(axisScaleDiv_private(axisId));
    }
}// class

class QwtPlotSeriesItem__<T> extends QwtPlotSeriesItem<T> {

    @QtBlockedSlot
    public final QwtSeriesData<T> data() {
        if (nativeId() == 0)
            throw new QNoNativeResourcesException("Function call on incomplete object of type: " +getClass().getName());
        return (QwtSeriesData<T>) __qt_data(nativeId());
    }
    @QtBlockedSlot
    private native final QwtSeriesData<T> __qt_data(long __this__nativeId);

    @QtBlockedSlot
    public final void setData(QwtSeriesData<T> data) {
        if (nativeId() == 0)
            throw new QNoNativeResourcesException("Function call on incomplete object of type: " +getClass().getName());
        if (data != null) {
            data.disableGarbageCollection();
        }
        final QwtSeriesData<T> oldData = (QwtSeriesData<T>)__qt_data(nativeId());
        __qt_setData(nativeId(), data == null ? 0 : data.nativeId());
        if (oldData != null) {
            oldData.reenableGarbageCollection();
        }
    }
    @QtBlockedSlot
    native void __qt_setData(long __this__nativeId, long data);

}// class

class QwtPlotCurve__ extends QwtPlotCurve {

    @QtBlockedSlot
    public final double closestPointDistance(com.trolltech.qt.core.QPoint point) {
        com.trolltech.qt.QNativePointer doublePointer = new com.trolltech.qt.QNativePointer(com.trolltech.qt.QNativePointer.Type.Double);
        closestPoint(point, doublePointer);
        return doublePointer.doubleValue();
    }

}// class

class QwtSymbol__ extends QwtSymbol {

    public final void setPen(com.trolltech.qt.gui.QColor color) {
        setPen(new com.trolltech.qt.gui.QPen(color));
    }
    public final void setBrush(com.trolltech.qt.gui.QColor color) {
        setBrush(new com.trolltech.qt.gui.QBrush(color));
    }
}// class
