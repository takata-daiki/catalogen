package android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewDebug.ExportedProperty;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.internal.R.styleable;

public class TableRow extends LinearLayout
{
    private ChildrenTracker mChildrenTracker;
    private SparseIntArray mColumnToChildIndex;
    private int[] mColumnWidths;
    private int[] mConstrainedColumnWidths;
    private int mNumColumns = 0;

    public TableRow(Context paramContext)
    {
        super(paramContext);
        initTableRow();
    }

    public TableRow(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        initTableRow();
    }

    private void initTableRow()
    {
        ViewGroup.OnHierarchyChangeListener localOnHierarchyChangeListener = this.mOnHierarchyChangeListener;
        this.mChildrenTracker = new ChildrenTracker(null);
        if (localOnHierarchyChangeListener != null)
            this.mChildrenTracker.setOnHierarchyChangeListener(localOnHierarchyChangeListener);
        super.setOnHierarchyChangeListener(this.mChildrenTracker);
    }

    private void mapIndexAndColumns()
    {
        if (this.mColumnToChildIndex == null)
        {
            int i = 0;
            int j = getChildCount();
            this.mColumnToChildIndex = new SparseIntArray();
            SparseIntArray localSparseIntArray = this.mColumnToChildIndex;
            for (int k = 0; k < j; k++)
            {
                LayoutParams localLayoutParams = (LayoutParams)getChildAt(k).getLayoutParams();
                if (localLayoutParams.column >= i)
                    i = localLayoutParams.column;
                int m = 0;
                while (m < localLayoutParams.span)
                {
                    int n = i + 1;
                    localSparseIntArray.put(i, k);
                    m++;
                    i = n;
                }
            }
            this.mNumColumns = i;
        }
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
        return paramLayoutParams instanceof LayoutParams;
    }

    protected LinearLayout.LayoutParams generateDefaultLayoutParams()
    {
        return new LayoutParams();
    }

    protected LinearLayout.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
        return new LayoutParams(paramLayoutParams);
    }

    public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
    {
        return new LayoutParams(getContext(), paramAttributeSet);
    }

    int getChildrenSkipCount(View paramView, int paramInt)
    {
        return -1 + ((LayoutParams)paramView.getLayoutParams()).span;
    }

    int[] getColumnsWidths(int paramInt)
    {
        int i = getVirtualChildCount();
        if ((this.mColumnWidths == null) || (i != this.mColumnWidths.length))
            this.mColumnWidths = new int[i];
        int[] arrayOfInt = this.mColumnWidths;
        int j = 0;
        if (j < i)
        {
            View localView = getVirtualChildAt(j);
            int k;
            if ((localView != null) && (localView.getVisibility() != 8))
            {
                LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
                if (localLayoutParams.span == 1)
                    switch (localLayoutParams.width)
                    {
                    default:
                        k = View.MeasureSpec.makeMeasureSpec(localLayoutParams.width, 1073741824);
                        label124: localView.measure(k, k);
                        arrayOfInt[j] = (localView.getMeasuredWidth() + localLayoutParams.leftMargin + localLayoutParams.rightMargin);
                    case -2:
                    case -1:
                    }
            }
            while (true)
            {
                j++;
                break;
                k = getChildMeasureSpec(paramInt, 0, -2);
                break label124;
                k = View.MeasureSpec.makeMeasureSpec(0, 0);
                break label124;
                arrayOfInt[j] = 0;
                continue;
                arrayOfInt[j] = 0;
            }
        }
        return arrayOfInt;
    }

    int getLocationOffset(View paramView)
    {
        return ((LayoutParams)paramView.getLayoutParams()).mOffset[0];
    }

    int getNextLocationOffset(View paramView)
    {
        return ((LayoutParams)paramView.getLayoutParams()).mOffset[1];
    }

    public View getVirtualChildAt(int paramInt)
    {
        if (this.mColumnToChildIndex == null)
            mapIndexAndColumns();
        int i = this.mColumnToChildIndex.get(paramInt, -1);
        if (i != -1);
        for (View localView = getChildAt(i); ; localView = null)
            return localView;
    }

    public int getVirtualChildCount()
    {
        if (this.mColumnToChildIndex == null)
            mapIndexAndColumns();
        return this.mNumColumns;
    }

    void measureChildBeforeLayout(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
        LayoutParams localLayoutParams;
        if (this.mConstrainedColumnWidths != null)
        {
            localLayoutParams = (LayoutParams)paramView.getLayoutParams();
            int i = 1073741824;
            int j = 0;
            int k = localLayoutParams.span;
            int[] arrayOfInt1 = this.mConstrainedColumnWidths;
            for (int m = 0; m < k; m++)
                j += arrayOfInt1[(paramInt1 + m)];
            int n = localLayoutParams.gravity;
            boolean bool = Gravity.isHorizontal(n);
            if (bool)
                i = -2147483648;
            paramView.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, j - localLayoutParams.leftMargin - localLayoutParams.rightMargin), i), getChildMeasureSpec(paramInt4, paramInt5 + (this.mPaddingTop + this.mPaddingBottom + localLayoutParams.topMargin + localLayoutParams.bottomMargin), localLayoutParams.height));
            if (bool)
            {
                int i1 = paramView.getMeasuredWidth();
                localLayoutParams.mOffset[1] = (j - i1);
                switch (0x7 & Gravity.getAbsoluteGravity(n, getResolvedLayoutDirection()))
                {
                case 2:
                case 3:
                case 4:
                default:
                case 5:
                case 1:
                }
            }
        }
        while (true)
        {
            return;
            localLayoutParams.mOffset[0] = localLayoutParams.mOffset[1];
            continue;
            localLayoutParams.mOffset[0] = (localLayoutParams.mOffset[1] / 2);
            continue;
            int[] arrayOfInt2 = localLayoutParams.mOffset;
            localLayoutParams.mOffset[1] = 0;
            arrayOfInt2[0] = 0;
            continue;
            super.measureChildBeforeLayout(paramView, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
        }
    }

    int measureNullChild(int paramInt)
    {
        return this.mConstrainedColumnWidths[paramInt];
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
    {
        super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
        paramAccessibilityEvent.setClassName(TableRow.class.getName());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
    {
        super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
        paramAccessibilityNodeInfo.setClassName(TableRow.class.getName());
    }

    protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
        layoutHorizontal();
    }

    protected void onMeasure(int paramInt1, int paramInt2)
    {
        measureHorizontal(paramInt1, paramInt2);
    }

    void setColumnCollapsed(int paramInt, boolean paramBoolean)
    {
        View localView = getVirtualChildAt(paramInt);
        if (localView != null)
            if (!paramBoolean)
                break label25;
        label25: for (int i = 8; ; i = 0)
        {
            localView.setVisibility(i);
            return;
        }
    }

    void setColumnsWidthConstraints(int[] paramArrayOfInt)
    {
        if ((paramArrayOfInt == null) || (paramArrayOfInt.length < getVirtualChildCount()))
            throw new IllegalArgumentException("columnWidths should be >= getVirtualChildCount()");
        this.mConstrainedColumnWidths = paramArrayOfInt;
    }

    public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener paramOnHierarchyChangeListener)
    {
        this.mChildrenTracker.setOnHierarchyChangeListener(paramOnHierarchyChangeListener);
    }

    private class ChildrenTracker
        implements ViewGroup.OnHierarchyChangeListener
    {
        private ViewGroup.OnHierarchyChangeListener listener;

        private ChildrenTracker()
        {
        }

        private void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener paramOnHierarchyChangeListener)
        {
            this.listener = paramOnHierarchyChangeListener;
        }

        public void onChildViewAdded(View paramView1, View paramView2)
        {
            TableRow.access$302(TableRow.this, null);
            if (this.listener != null)
                this.listener.onChildViewAdded(paramView1, paramView2);
        }

        public void onChildViewRemoved(View paramView1, View paramView2)
        {
            TableRow.access$302(TableRow.this, null);
            if (this.listener != null)
                this.listener.onChildViewRemoved(paramView1, paramView2);
        }
    }

    public static class LayoutParams extends LinearLayout.LayoutParams
    {
        private static final int LOCATION = 0;
        private static final int LOCATION_NEXT = 1;

        @ViewDebug.ExportedProperty(category="layout")
        public int column;
        private int[] mOffset = new int[2];

        @ViewDebug.ExportedProperty(category="layout")
        public int span;

        public LayoutParams()
        {
            super(-2);
            this.column = -1;
            this.span = 1;
        }

        public LayoutParams(int paramInt)
        {
            this();
            this.column = paramInt;
        }

        public LayoutParams(int paramInt1, int paramInt2)
        {
            super(paramInt2);
            this.column = -1;
            this.span = 1;
        }

        public LayoutParams(int paramInt1, int paramInt2, float paramFloat)
        {
            super(paramInt2, paramFloat);
            this.column = -1;
            this.span = 1;
        }

        public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
        {
            super(paramAttributeSet);
            TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.TableRow_Cell);
            this.column = localTypedArray.getInt(0, -1);
            this.span = localTypedArray.getInt(1, 1);
            if (this.span <= 1)
                this.span = 1;
            localTypedArray.recycle();
        }

        public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
        {
            super();
        }

        public LayoutParams(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
        {
            super();
        }

        protected void setBaseAttributes(TypedArray paramTypedArray, int paramInt1, int paramInt2)
        {
            if (paramTypedArray.hasValue(paramInt1))
            {
                this.width = paramTypedArray.getLayoutDimension(paramInt1, "layout_width");
                if (!paramTypedArray.hasValue(paramInt2))
                    break label48;
            }
            label48: for (this.height = paramTypedArray.getLayoutDimension(paramInt2, "layout_height"); ; this.height = -2)
            {
                return;
                this.width = -1;
                break;
            }
        }
    }
}

/* Location:                     /home/lithium/miui/chameleon/2.11.16/framework_dex2jar.jar
 * Qualified Name:         android.widget.TableRow
 * JD-Core Version:        0.6.2
 */