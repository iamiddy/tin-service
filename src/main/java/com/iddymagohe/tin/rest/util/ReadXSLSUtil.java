package com.iddymagohe.tin.rest.util;

/*
* This Utility class reads a list of businesses from MS-EXCEL file
* Only the first sheet of the workbook is considered,the sheet name is the Tax-region
* The program locates a row with values (S/NO,NAME,TIN,NATURE OF BUSINESS,LOCATION)
* and start reading data of the following (next)row.Each row  is an  Instance of @Business.
*
* */

import com.iddymagohe.tin.domain.Business;
import com.iddymagohe.tin.domain.TaxRegion;
import com.iddymagohe.tin.repositories.BusinessRepository;
import com.iddymagohe.tin.repositories.TaxRegionRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * @author iddymagohe
 * Created by iddymagohe on 8/3/14.
 */

@Service
public class ReadXSLSUtil implements ResourceLoaderAware {
    public static final String  XLS_FOLDER="file:/Users/iddymagohe/JavaProjects/dev-apps/tin-xls/";
    public static final Logger logger= Logger.getLogger(ReadXSLSUtil.class);
    public static final String TIN_REGEX= "^[0-9-]{9,11}$";

    @Autowired
    TaxRegionRepository taxRegionRepository;

    @Autowired
    protected BusinessRepository businessRepository;


    String [] columns= {"S/NO","NAME","TIN","NATURE OF BUSINESS","LOCATION"};
    /* Attribute/Element = column Index
    *   tinNumber = 2;
    *   region = region;
    *   businessName = 1;
    *   natureOfBusiness = 3;
    *   address = 4;
    * */
    Boolean [] columnsFound=new Boolean[]{false,false,false,false,false};

    private ResourceLoader resourceLoader;

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Resource getResource(String location){
        return resourceLoader.getResource(location);
    }


    public  void readFileData(String fileName) {
        Set<Business> businesses= new HashSet<>();
        Workbook workbook = null;
        boolean foundFirstRow=false;
        try (FileInputStream fis = new FileInputStream(getResource(XLS_FOLDER+fileName).getFile())) {

            if (fileName.toLowerCase().endsWith("xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (fileName.toLowerCase().endsWith("xls")) {
                workbook = new HSSFWorkbook(fis);
            }

           //get the first sheet, assign the sheet name to @region
            Sheet sheet = workbook.getSheetAt(0);
            TaxRegion  region=taxRegionRepository.findByRegionIgnoreCase(sheet.getSheetName().trim());
            if(null ==region){
                region =new TaxRegion(sheet.getSheetName().trim());
                taxRegionRepository.save(region);
            }

            //every sheet has rows, iterate over them
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row= rowIterator.next();

                //Check the first row with data
                if(!foundFirstRow){
                    boolean temp=false;

                    foundFirstRow=columnsFound[1] && columnsFound[2] && columnsFound[3];
                }

                Business business=null;
                /*String tin=""; 2
                String name=""; 1
                String natureOfBus="";3
                String location=""; 4 */

                String[] rowValues= new String[5];

                //Every row has columns, get the column iterator and iterate over them
                Iterator<Cell> cellIterator = row.cellIterator();
                int cellIndex=0;
                while (cellIterator.hasNext())
                {
                //Get the Cell object
                    Cell cell = cellIterator.next();
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    if(cellIndex > 4) break;

                    if(foundFirstRow){
                        rowValues[cellIndex]=cell.getStringCellValue().trim();

                    }else if(columns[cellIndex].equalsIgnoreCase(cell.getStringCellValue().trim())){
                        columnsFound[cellIndex]=true;
                    }
                    cellIndex++;

                } //end of cell iterator
                //Striping '-' from @param tinNumber
                if(foundFirstRow) {
                    String tin=rowValues[2];
                    String name= rowValues[1];
                    String tempV="";
                    if(!tin.matches(TIN_REGEX) && name.matches(TIN_REGEX)){
                        tempV=tin;
                        tin=name;
                        name=tempV;

                    }
                    business = new Business(StringUtils.remove(tin, '-'), region, name, rowValues[3], rowValues[4]);
                    businesses.add(business);
                }
            }

        } catch (IOException e) {
            logger.log(Priority.ERROR ,e.getMessage());

        }

      businessRepository.save(businesses);
       // businessRepository.deleteAll();
        logger.log(Priority.INFO,"Loaded business & Saved " + businesses.size());
        businesses.clear();

        //save collection of businesses
    }



}
