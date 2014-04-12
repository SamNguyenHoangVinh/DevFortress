/*
 * @author Aoi Mizu
 */

package model.skill;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class LoadSkills {
    
    public LoadSkills(SkillsList skills) throws Exception {
        String filename = "src/data/ISYS2102_SE2_Skills.xls";
        List list = new ArrayList();
        FileInputStream input = new FileInputStream(filename);
        
        try {

            // Create an excel workbook from the file input
            HSSFWorkbook workbook = new HSSFWorkbook(input);

            // Get the first sheet on the workbook.
            HSSFSheet sheet = workbook.getSheetAt(0);
 
            // Read each rows of the sheet
            Iterator rows = sheet.rowIterator();
            while (rows.hasNext()) {
                HSSFRow row = (HSSFRow) rows.next();
                
                // Read each cell of the row
                Iterator cells = row.cellIterator();

                List data = new ArrayList();
                while (cells.hasNext()) {
                    HSSFCell cell = (HSSFCell) cells.next();
                    data.add(cell);
                }
                list.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        showExcelData(list, skills);
    }
    
    private static void showExcelData(List sheetData, SkillsList skills) {
        //
        // Iterates the data and print it out to the console.
        //
        HSSFCell type = null;
        for (int i = 0; i < sheetData.size(); i++) {
            List list = (List) sheetData.get(i);
            if(i!=0 && i!=1) {
                HSSFCell id = (HSSFCell) list.get(0);
                HSSFCell name = null;
                HSSFCell start = null;
                HSSFCell formula = null;
                Exception e = null;
                try {
                    name = (HSSFCell) list.get(2);
                    String s = name.getStringCellValue();
                }
                catch (Exception ex) {
                    e = ex;
                }
                if (e==null) {
                    type = (HSSFCell) list.get(1);
                    start = (HSSFCell) list.get(3);
                    formula = (HSSFCell) list.get(4);
                }
                else if (e instanceof NumberFormatException) {
                    name = (HSSFCell) list.get(1);
                    start = (HSSFCell) list.get(2);
                    formula = (HSSFCell) list.get(3);
                }
                int sId = (int) id.getNumericCellValue();
                String sType = type.getStringCellValue();
                String sName = name.getStringCellValue();
                int sStart = (int) start.getNumericCellValue();
                String sFormula = formula.getStringCellValue();
                if(sFormula.equals("cost(n) = cost(n-1) + 2")) {
//                    skills.add(new Skill(sName, sType, sStart, new SkillPlus2()));
                    skills.add(new Skill(sName, sType, sStart, Skill.PLUS_2));
                }
                else if(sFormula.equals("cost(n) = cost(n-1) + 4")) {
//                    skills.add(new Skill(sName, sType, sStart, new SkillPlus4()));
                    skills.add(new Skill(sName, sType, sStart, Skill.PLUS_4));
                }
                else if(sFormula.equals("cost(n) = cost(n-1) * 2")) {
//                    skills.add(new Skill(sName, sType, sStart, new SkillTime2()));
                    skills.add(new Skill(sName, sType, sStart, Skill.TIME_2));
                }
            }
        }
    }
}
