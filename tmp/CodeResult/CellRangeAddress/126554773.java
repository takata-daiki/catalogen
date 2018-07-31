/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule.xslparser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import schedule.shcedule.Group;
import schedule.shcedule.Lesson;
import schedule.shcedule.Schedule;

/**
 *
 * @author president
 */
public class XSLGenerator {

    public static void generate(Schedule schedule, File name) {
        try {
            FileOutputStream out = new FileOutputStream(name);
            Workbook wb = new HSSFWorkbook();
            Sheet s = wb.createSheet();
            int curCol = 4;
            int curRow = 2;
            ArrayList<Group> groups = schedule.getGroupsByCours((short) 1, Group.BACHELOR);
            int numOfGroups = groups.size();
            s.addMergedRegion(new CellRangeAddress(curRow, curRow,
                    curCol, curCol + numOfGroups - 1));
            Row row1 = s.createRow(curRow);
            Row row2 = s.createRow(curRow + 2);
            Row row3 = s.createRow(curRow + 3);
            Cell cell = row1.createCell(curCol);
            cell.setCellValue("1 Курс");
            int i = curCol - 1;
            int j = 1;
            String curSpec = "";
            for (Group group : groups) {
                if (!curSpec.equals(group.getSpecialty())) {
                    curSpec = group.getSpecialty();
                    s.addMergedRegion(new CellRangeAddress(curRow + 3, curRow + 3,
                            i, i + j - 1));
                    i += j;
                    j = 0;
                    row3.createCell(i).setCellValue(curSpec);
                }
                cell = row2.createCell(i + j);
                cell.setCellValue(group.getName());
                j++;
            }
            curCol += numOfGroups + 2;
            groups = schedule.getGroupsByCours((short) 2, Group.BACHELOR);
            numOfGroups = groups.size();
            s.addMergedRegion(new CellRangeAddress(curRow, curRow,
                    curCol, curCol + numOfGroups - 1));
            cell = row1.createCell(curCol);
            cell.setCellValue("2 Курс");
            i = curCol - 1;
            j = 1;
            curSpec = "";
            for (Group group : groups) {
                if (!curSpec.equals(group.getSpecialty())) {
                    curSpec = group.getSpecialty();
                    s.addMergedRegion(new CellRangeAddress(curRow + 3, curRow + 3,
                            i, i + j - 1));
                    i += j;
                    j = 0;
                    row3.createCell(i).setCellValue(curSpec);
                }
                cell = row2.createCell(i + j);
                cell.setCellValue(group.getName());
                j++;
            }
            curCol += numOfGroups + 2;
            groups = schedule.getGroupsByCours((short) 3, Group.BACHELOR);
            numOfGroups = groups.size();
            s.addMergedRegion(new CellRangeAddress(curRow, curRow,
                    curCol, curCol + numOfGroups - 1));
            cell = row1.createCell(curCol);
            cell.setCellValue("3 Курс");
            i = curCol - 1;
            j = 1;
            curSpec = "";
            for (Group group : groups) {
                if (!curSpec.equals(group.getSpecialty())) {
                    curSpec = group.getSpecialty();
                    s.addMergedRegion(new CellRangeAddress(curRow + 3, curRow + 3,
                            i, i + j - 1));
                    i += j;
                    j = 0;
                    row3.createCell(i).setCellValue(curSpec);
                }
                cell = row2.createCell(i + j);
                cell.setCellValue(group.getName());
                j++;
            }
            curCol += numOfGroups + 2;
            groups = schedule.getGroupsByCours((short) 4, Group.BACHELOR);
            numOfGroups = groups.size();
            s.addMergedRegion(new CellRangeAddress(curRow, curRow,
                    curCol, curCol + numOfGroups - 1));
            cell = row1.createCell(curCol);
            cell.setCellValue("4 Курс");
            i = curCol - 1;
            j = 1;
            curSpec = "";
            for (Group group : groups) {
                if (!curSpec.equals(group.getSpecialty())) {
                    curSpec = group.getSpecialty();
                    s.addMergedRegion(new CellRangeAddress(curRow + 3, curRow + 3,
                            i, i + j - 1));
                    i += j;
                    j = 0;
                    row3.createCell(i).setCellValue(curSpec);
                }
                cell = row2.createCell(i + j);
                cell.setCellValue(group.getName());
                j++;
            }
            curCol += numOfGroups + 2;
            groups = schedule.getGroupsByCours((short) 1, Group.SPECIALIST);
            numOfGroups = groups.size();
            s.addMergedRegion(new CellRangeAddress(curRow, curRow,
                    curCol, curCol + numOfGroups - 1));
            cell = row1.createCell(curCol);
            cell.setCellValue("1курс спец");
            i = curCol - 1;
            j = 1;
            curSpec = "";
            for (Group group : groups) {
                if (!curSpec.equals(group.getSpecialty())) {
                    curSpec = group.getSpecialty();
                    s.addMergedRegion(new CellRangeAddress(curRow + 3, curRow + 3,
                            i, i + j - 1));
                    i += j;
                    j = 0;
                    row3.createCell(i).setCellValue(curSpec);
                }
                cell = row2.createCell(i + j);
                cell.setCellValue(group.getName());
                j++;
            }
            curCol += numOfGroups + 2;
            groups = schedule.getGroupsByCours((short) 1, Group.MASTER);
            numOfGroups = groups.size();
            s.addMergedRegion(new CellRangeAddress(curRow, curRow,
                    curCol, curCol + numOfGroups - 1));
            cell = row1.createCell(curCol);
            cell.setCellValue("1 курс \"Магістри\"");
            i = curCol - 1;
            j = 1;
            curSpec = "";
            for (Group group : groups) {
                if (!curSpec.equals(group.getSpecialty())) {
                    curSpec = group.getSpecialty();
                    s.addMergedRegion(new CellRangeAddress(curRow + 3, curRow + 3,
                            i, i + j - 1));
                    i += j;
                    j = 0;
                    row3.createCell(i).setCellValue(curSpec);
                }
                cell = row2.createCell(i + j);
                cell.setCellValue(group.getName());
                j++;
            }
            curCol += numOfGroups + 2;
            groups = schedule.getGroupsByCours((short) 2, Group.MASTER);
            numOfGroups = groups.size();
            s.addMergedRegion(new CellRangeAddress(curRow, curRow,
                    curCol, curCol + numOfGroups - 1));
            cell = row1.createCell(curCol);
            cell.setCellValue("2 курс \"Магістри\"");
            i = curCol - 1;
            j = 1;
            curSpec = "";
            for (Group group : groups) {
                if (!curSpec.equals(group.getSpecialty())) {
                    curSpec = group.getSpecialty();
                    s.addMergedRegion(new CellRangeAddress(curRow + 3, curRow + 3,
                            i, i + j - 1));
                    i += j;
                    j = 0;
                    row3.createCell(i).setCellValue(curSpec);
                }
                cell = row2.createCell(i + j);
                cell.setCellValue(group.getName());
                j++;
            }
            curRow = 7;
            curCol = 1;
            row1 = s.createRow(curRow);
            int numOfLessons = schedule.getNumOfLessonsOnDay();
            row1.createCell(curCol).setCellValue("Понеділок");
            s.addMergedRegion(new CellRangeAddress(curRow, curRow + numOfLessons * 4 - 1,
                    curCol, curCol));
            for (i = 0; i < numOfLessons; i++) {
                s.addMergedRegion(new CellRangeAddress(curRow + i * 4, curRow + (i + 1) * 4 - 1,
                        curCol + 1, curCol + 1));
                if (i == 0) {
                    s.getRow(curRow + i * 4).createCell(curCol + 1).setCellValue(getTimeString(schedule, i));
                } else {
                    s.createRow(curRow + i * 4).createCell(curCol + 1).setCellValue(getTimeString(schedule, i));
                }
            }
            curRow += numOfLessons * 4 + 10;
            row1 = s.createRow(curRow);
            row1.createCell(curCol).setCellValue("Вівторок");
            s.addMergedRegion(new CellRangeAddress(curRow, curRow + numOfLessons * 4 - 1,
                    curCol, curCol));
            for (i = 0; i < numOfLessons; i++) {
                s.addMergedRegion(new CellRangeAddress(curRow + i * 4, curRow + (i + 1) * 4 - 1,
                        curCol + 1, curCol + 1));
                if (i == 0) {
                    s.getRow(curRow + i * 4).createCell(curCol + 1).setCellValue(getTimeString(schedule, i));
                } else {
                    s.createRow(curRow + i * 4).createCell(curCol + 1).setCellValue(getTimeString(schedule, i));
                }
            }
            curRow += numOfLessons * 4 + 10;
            row1 = s.createRow(curRow);
            row1.createCell(curCol).setCellValue("Середа");
            s.addMergedRegion(new CellRangeAddress(curRow, curRow + numOfLessons * 4 - 1,
                    curCol, curCol));
            for (i = 0; i < numOfLessons; i++) {
                s.addMergedRegion(new CellRangeAddress(curRow + i * 4, curRow + (i + 1) * 4 - 1,
                        curCol + 1, curCol + 1));
                if (i == 0) {
                    s.getRow(curRow + i * 4).createCell(curCol + 1).setCellValue(getTimeString(schedule, i));
                } else {
                    s.createRow(curRow + i * 4).createCell(curCol + 1).setCellValue(getTimeString(schedule, i));
                }
            }
            curRow += numOfLessons * 4 + 10;
            row1 = s.createRow(curRow);
            row1.createCell(curCol).setCellValue("Четвер");
            s.addMergedRegion(new CellRangeAddress(curRow, curRow + numOfLessons * 4 - 1,
                    curCol, curCol));
            for (i = 0; i < numOfLessons; i++) {
                s.addMergedRegion(new CellRangeAddress(curRow + i * 4, curRow + (i + 1) * 4 - 1,
                        curCol + 1, curCol + 1));
                if (i == 0) {
                    s.getRow(curRow + i * 4).createCell(curCol + 1).setCellValue(getTimeString(schedule, i));
                } else {
                    s.createRow(curRow + i * 4).createCell(curCol + 1).setCellValue(getTimeString(schedule, i));
                }
            }
            curRow += numOfLessons * 4 + 10;
            row1 = s.createRow(curRow);
            row1.createCell(curCol).setCellValue("П'ятниця");
            s.addMergedRegion(new CellRangeAddress(curRow, curRow + numOfLessons * 4 - 1,
                    curCol, curCol));
            for (i = 0; i < numOfLessons; i++) {
                s.addMergedRegion(new CellRangeAddress(curRow + i * 4, curRow + (i + 1) * 4 - 1,
                        curCol + 1, curCol + 1));
                if (i == 0) {
                    s.getRow(curRow + i * 4).createCell(curCol + 1).setCellValue(getTimeString(schedule, i));
                } else {
                    s.createRow(curRow + i * 4).createCell(curCol + 1).setCellValue(getTimeString(schedule, i));
                }
            }
            int k = 0;
            row1 = s.getRow(4);
            curCol = 4;
            while (k < schedule.getNumOfGroups()) {
                try {
                    String groupName = row1.getCell(curCol).getStringCellValue();
                    if (groupName.length() > 0) {
                        for (j = 0; j < 5; j++) {
                            for (i = 0; i < numOfLessons; i++) {
                                int q = 0;
                                for (Lesson les : schedule.getLessons(i+1, j + 1, schedule.getGroup(groupName))) {
                                    try {
                                        s.getRow(7 + j * (4 * numOfLessons + 10) + i * 4 + q).createCell(curCol).setCellValue(les.toString());
                                    } catch (NullPointerException ex) {
                                        s.createRow(7 + j * (4 * numOfLessons + 10) + i * 4 + q).createCell(curCol).setCellValue(les.toString());
                                    }
                                    q++;
                                }
                            }
                        }
                        k++;
                    }
                } catch (NullPointerException ex) {
                }
                curCol++;
            }
            wb.write(out);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static String getTimeString(Schedule schedule, int k) {
        String time = schedule.getRingTime(k);
        long timel = Math.round(Math.round(Double.parseDouble(time)) * 60 + Double.parseDouble(time) % 1 * 100);
        time += "-" + new Double((timel + 45) / 60 + (timel + 45) % 60 / 100.0).toString()
                + ", " + new Double((timel + 50) / 60 + (timel + 50) % 60 / 100.0).toString()
                + "-" + new Double((timel + 95) / 60 + (timel + 95) % 60 / 100.0).toString();
        return time;
    }
}
