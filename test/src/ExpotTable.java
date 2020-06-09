package com.mobile.main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportTable {

	public String exportTable() {
		//得到所有需要导出的表信息，一个表放在一个sheet中
		List<ExportTableBean> exportTableBean = getTableBeanService.getAllTable();
		//导出文件路径（这里也可以写成固定路径"D:/tmp"）
		String filePath = System.getProperty("catalina.base") + File.separator + "tmp";
		//创建此路径
		makeDirs(filePath);
		//得到此路径下文件
		File fileDir = new File(filePath);
		File[] fileDirs = fileDir.listFiles();
		//删除此目录下文件
		for (int i = 0; i < fileDirs.length; i++) {
			File tmp = fileDirs[i];
			tmp.delete();
		}
		//导出Excel文件路径
		String fullFilePath = "";
		//导出压缩文件路径
		String zipFilePath = "";
		//保存文件名
		String fileName = "";
		FileOutputStream os = null;
		//每个Excel文件中第一个sheet中的表对应list中的开始下标
		int index = 0;
		//Excel的个数-1，用来计算list开始下标
		int count = 0;
		try {
			//如果表个数不超过254个，则导出Excel文件，否则导出压缩文件
			if(null != exportTableBean && exportTableBean.size() > SheetNumber){
				//计算需要导出的表个数，即sheet个数
				int size = exportTableBean.size();
				//用于存放生成的excel文件名称  
				List<String> fileNames = new ArrayList<String>();
				//导出压缩文件的全路径
				zipFilePath = filePath + File.separator + "cwm_" + System.currentTimeMillis() + ".zip";
				File zip = new File(zipFilePath);
				while (size - SheetNumber > 0 || size > 0) {
					fileName = "cwm_" + System.currentTimeMillis() + ".xlsx";
					//导出excel的全路径
					fullFilePath = filePath + File.separator + fileName;
					fileNames.add(fullFilePath);
					os = new FileOutputStream(fullFilePath);
					index = count * SheetNumber;
					//list中表依次导入excel文件
					XSSFWorkbook wb = createColumnXSSF(exportTableBean,index);
					// 写文件
					wb.write(os);
					count++;
					size -= SheetNumber;
				}
				//将excel文件生成压缩文件
				File srcfile[] = new File[fileNames.size()];  
		        for (int j = 0, n1 = fileNames.size(); j < n1; j++) {  
		            srcfile[j] = new File(fileNames.get(j));  
		        }  
		        ZipFiles(srcfile, zip);  
			}else{
				fileName = "cwm_" + System.currentTimeMillis() + ".xlsx";
				zipFilePath = filePath + File.separator + fileName;
				os = new FileOutputStream(zipFilePath);
				//list中表依次导入excel文件
				XSSFWorkbook wb = createColumnXSSF(exportTableBean,0);
				// 写文件
				wb.write(os);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			// 关闭输出流
			if(null != os){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return zipFilePath;
	}
	
	
	 //压缩文件  
    public void ZipFiles(File[] srcfile, File zipfile) {  
        byte[] buf = new byte[1024];  
        try {  
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(  
                    zipfile));  
            for (int i = 0; i < srcfile.length; i++) {  
                FileInputStream in = new FileInputStream(srcfile[i]);  
                out.putNextEntry(new ZipEntry(srcfile[i].getName()));  
                int len;  
                while ((len = in.read(buf)) > 0) {  
                    out.write(buf, 0, len);  
                }  
                out.closeEntry();  
                in.close();  
            }  
            out.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    } 
    
        /**
	 * 创建目标路径中文件夹
	 * @param folderName
	 * @return
	 */
	public static boolean makeDirs(String folderName) {
		if (folderName == null || folderName.isEmpty()) {
			return false;
		}
		File folder = new File(folderName);
		return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
	}
	
	/**
	 * 将表信息写入Excel
	 * @param sheet
	 * @param metadataList
	 * @return
	 */
	public XSSFWorkbook createColumnXSSF(List<ExportTableBean> exportTableBeanList,int start) {
		//创建workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		//创建样式
		XSSFCellStyle cellStyle =workbook.createCellStyle();
		cellStyle.setWrapText(true);//自动换行
		XSSFSheet sheet_first = workbook.createSheet("说明");// 第一个表不填
		// 合并单元格（开始行，结束行，开始列，结束列）
		sheet_first.addMergedRegion(new CellRangeAddress(0, 2, 0, 4));
		Cell cell = sheet_first.createRow(0).createCell(0);
		cell.setCellValue("    一个sheet放一个表的信息，第一个sheet放说明等信息，真正的表信息数据从第二个sheet开始。\n sheet名为表名+'_'+目前表个数");
		cell.setCellStyle(cellStyle);
		int sheetCount = 1;
		String sheetName = "";
		for(int k = start;k < (start + SheetNumber);k++){
			//如果list的size小于等于k,就停止
			if(exportTableBeanList.size() <= k){
				break;
			}
			//list是有序的，从k开始
			ExportTableBean exportTableBean = exportTableBeanList.get(k);
			// 给每个元数据表创建一个sheet
			//为了sheet名不重复，对表名进行截取（Excel中sheet名不得超过31位）
			sheetName = exportTableBean.getTableName();
			if(sheetName.length() > 27){
				sheetName = sheetName.substring(0, 27);
			}
			//每个excel文件最多容纳255sheet
			if(sheetCount > 255){
				break;
			}
			//sheet名为表名_表个数
			XSSFSheet sheet = workbook.createSheet(sheetName + "_" + (sheetCount++));
			// 纵向固定值写入
			
			sheet.autoSizeColumn(1, true);
			//index记录当前行数
			int index = 0;
			// 把表版本写入
			sheet.createRow(index++).createCell(1).setCellValue(exportTableBean.getVersion());
			// 把表名写到Excel表中
			sheet.createRow(index++).createCell(1).setCellValue(exportTableBean.getTableName());
			// 把表中文名称写入
			sheet.createRow(index++).createCell(1).setCellValue(exportTableBean.getChineseName());
			
			int rowNum = sheet.getLastRowNum();
			// 合并单元格
			int coloumNum = sheet.getRow(sheet.getLastRowNum()).getPhysicalNumberOfCells();
			if(coloumNum >= 1){
				for (int i = 0; i <= (rowNum + 1); i++) {
					sheet.addMergedRegion(new CellRangeAddress(i,i, 1, coloumNum - 1));
				}
			}
			sheet.setDefaultColumnWidth(10);
			//cellStyle.setWrapText(true);//自动换行
		}
		return workbook;
	}
	
	  @GET
	  @Path("/{userId}/table/export")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Response exportMetadata(@PathParam("userId") int userId,@QueryParam("ids") String ids) throws Exception {
	    ResponseBuilder responseBuilder = null;
	    String filePathDetail = exportService.exportTable();
	    try {
	      File file = new File(filePathDetail);
	      //System.out.println(filePathDetail);
	      String filename = file.getName();
	      InputStream inputStream = new BufferedInputStream(new FileInputStream(filePathDetail));

	      responseBuilder = Response.ok(inputStream);

	      responseBuilder.header("Content-Disposition",
	          "attachment;filename=" + new String(filename.getBytes("utf-8"), "ISO-8859-1"));
	      responseBuilder.header("Content-Type", "text/plain");
	    } catch (Exception e) {
	      OperLog.logOperation(logger, userId, request, "exportTable", filePathDetail,
	          "export table", "failed");
	      e.printStackTrace();
	    }
	    return responseBuilder.build();
	  }
}
