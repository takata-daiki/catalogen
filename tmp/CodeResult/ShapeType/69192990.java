package com.androiddraw.geometry;

import com.androiddraw.Draw;
import com.androiddraw.R;

import android.content.res.Resources;

public enum ShapeType
{
    Unknown,
    Circle,

    // Quadrilaterals
    Trapezoid,
    Parallelogram,
    Rectangle,
    Square,
    Rhombus,

    // Triangles
    EquilateralTriangle,
    IsoscelesTriangle,
    RectangledTriangle,
    RectangledIsoscelesTriangle;

    @Override
    public String toString()
    { 
        Resources res = Draw.resources;
        if (res != null)
        {
            switch (this)
            {
                case Circle:
                    return res.getString(R.string.circle);
                case Trapezoid:
                    return res.getString(R.string.trapezoid);
                case Parallelogram:
                    return res.getString(R.string.parallelogram);
                case Rectangle:
                    return res.getString(R.string.rectangle);
                case Square:
                    return res.getString(R.string.square);
                case Rhombus:
                    return res.getString(R.string.rhombus);
                case EquilateralTriangle:
                    return res.getString(R.string.equilateralTriangle);
                case IsoscelesTriangle:
                    return res.getString(R.string.isoscelesTriangle);
                case RectangledTriangle:
                    return res.getString(R.string.rectangledTriangle);
                case RectangledIsoscelesTriangle:
                    return res.getString(R.string.rectangledIsoscelesTriangle);
                default:
                    return res.getString(R.string.unknown);
            }
        }

        return super.toString();
    }
}
