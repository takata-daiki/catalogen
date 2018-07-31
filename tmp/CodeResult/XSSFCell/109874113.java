package models;

import models.FrontType;
import models.Product;
import models.ProductType;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import play.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: larsduvaas
 * Date: Aug 6, 2010
 * Time: 1:17:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductBuilder {

//    private String width;
    private Integer width_x;
    private Integer width_y;
    private Integer depth;
    private Integer height;
    private String name;
    private String description;
    private ProductType pt = null;
    private ProductCategory pc;

    private Integer row = -1;

    private enum Column {
        A, B, C, D, E, F, G, H, I, J, K, L, M, N, O;


        boolean isName() {
            return this == A;
        }

        boolean isDescription() {
            return this == B;
        }

        boolean isWidth() {
            return this == C;
        }

        boolean isHeight() {
            return this == D;
        }

        boolean isDepth() {
            return this == E;
        }

        boolean isProduct() {
            return this == G ||
                    this == I || this == K || this == M || this == O;
        }

        FrontType getFrontType() {
            switch (this) {
                default:
                    throw new IllegalStateException("no front for row " + this);
                case G:
                    return FrontType.without;
                case I:
                    return FrontType.white_soft_melamine;
                case K:
                    return FrontType.white_soft_melamine_with_gray;
                case M:
                    return FrontType.white_mdf;
                case O:
                    return FrontType.white_high_gloss_film;
            }
        }

        static Column get(XSSFCell cell) {
            try {
                return Column.get(cell.getReference());
            } catch (Exception e) {
                return null;
            }
        }

        static Column get(String reference) {
            try {
                return Column.valueOf(reference.substring(0, 1));
            } catch (Exception e) {
                return null;
            }
        }


    }

    public void addCell(XSSFCell cell) {
        Logger.error("reference: %s, row: %s, column:%s, value:'%s'", cell.getReference(), cell.getRowIndex(), cell.getColumnIndex(), cell.toString());
        if (row != cell.getRowIndex()) {
            row = cell.getRowIndex();
        }
        if (skip())
            return;
        if (StringUtils.isBlank(cell.toString())) {
            return;
        }


        Column col = Column.get(cell);
        if (col == null)
            return;

        if (col.isName()) {
            if (cell.toString().contains("new_pt")) {
                newPT(cell);
            } else {
                pt.name = cell.toString();
                name = cell.toString();
            }
            if (cell.toString().contains("pc=")) {
                pc = findPC(cell.toString());
                if (pt != null) {
                    pt.productCategory = pc;
                    pt.save();
                }
            }
        }

        if (col.isDepth()) {
            depth = Double.valueOf(cell.toString()).intValue();
            if (pt != null && pt.depth == null)
                pt.depth = depth;
            return;
        }

        if (col.isWidth()) {
            setWidth(cell);
            return;
        }
        if (col.isHeight()) {
            height = Double.valueOf(cell.toString()).intValue();
            if (pt != null && pt.height == null) {
                pt.height = height;
                pt.save();
            }
            return;
        }
        if (col.isDescription()) {
            if (cell.toString().contains("null"))
                description = null;
            else
                description = cell.toString();
            if (pt != null) {
                pt.description = description;
                pt.save();
            }
        }

        if (col.isProduct()) {
            Product product = new Product();
            product.productType = pt;
            product.frontType = col.getFrontType();
            product.width_x = width_x;
            product.width_y = width_y;
            if (pt == null) {
                product.depth = depth;
                product.height = height;
                product.name = name;
                product.description = description;
                product.productCategory = pc;
            } else {
                if (pt.height != null && !pt.height.equals(height))
                    product.height = height;
                if (pt.depth != null && !pt.depth.equals(depth))
                    product.depth = depth;
            }
            product.priceExpression = replaceValues(cell.toString());
            product.productNumber = calculateProductNumber(cell.getReference());
            product.save();
        }

    }

    private boolean skip() {

        return row < 10
                || (row > 52 && row < 67)
                || (row > 100 && row < 122)
//                || (row > 161 && row <176)
//                || (row > 52 && row <67)
//                || (row > 52 && row <67)
                || row > 161;
    }

    private void setWidth(XSSFCell cell) {
        String value = cell.toString();
        if (value.matches("\\d*\\/\\d*")) {
            String[] values = value.split("/");
            width_x = Integer.valueOf(values[0]);
            width_y = Integer.valueOf(values[1]);
        } else {
            width_x = Double.valueOf(value).intValue();
            width_y = null;
        }
    }

    private ProductCategory findPC(String cellValue) {
        Pattern pattern = Pattern.compile("pc=(.*)");
        Matcher matcher = pattern.matcher(cellValue);
        if (matcher.find()) {
            String pc_string = matcher.group(1);
            return ProductCategory.valueOf(pc_string);
        }
        throw new IllegalArgumentException("Did not find ProductCategory in " + cellValue);
    }

    private String replaceValues(String priceFormula) {
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("[A-Z]\\d+");
        Matcher matcher = pattern.matcher(priceFormula);
        while (matcher.find()) {
            String reference = matcher.group();
            matcher.appendReplacement(sb, "p" + calculateProductNumber(reference));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private String calculateProductNumber(String reference) {
        int row = Integer.valueOf(reference.substring(1));
        return StringUtils.leftPad(((row * 1000) + Column.get(reference).ordinal()) + "", 6, '0');
    }

    private void newPT(XSSFCell cell) {
        ProductType type = new ProductType();
        if (pt != null) {
            type.name = pt.name;
            type.description = pt.description;
            type.productCategory = pt.productCategory;
            pt.save();
        }
        pt = type;
        pt.save();
    }
}
