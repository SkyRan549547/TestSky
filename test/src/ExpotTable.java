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
		//�õ�������Ҫ�����ı���Ϣ��һ�������һ��sheet��
		List<ExportTableBean> exportTableBean = getTableBeanService.getAllTable();
		//�����ļ�·��������Ҳ����д�ɹ̶�·��"D:/tmp"��
		String filePath = System.getProperty("catalina.base") + File.separator + "tmp";
		//������·��
		makeDirs(filePath);
		//�õ���·�����ļ�
		File fileDir = new File(filePath);
		File[] fileDirs = fileDir.listFiles();
		//ɾ����Ŀ¼���ļ�
		for (int i = 0; i < fileDirs.length; i++) {
			File tmp = fileDirs[i];
			tmp.delete();
		}
		//����Excel�ļ�·��
		String fullFilePath = "";
		//����ѹ���ļ�·��
		String zipFilePath = "";
		//�����ļ���
		String fileName = "";
		FileOutputStream os = null;
		//ÿ��Excel�ļ��е�һ��sheet�еı��Ӧlist�еĿ�ʼ�±�
		int index = 0;
		//Excel�ĸ���-1����������list��ʼ�±�
		int count = 0;
		try {
			//��������������254�����򵼳�Excel�ļ������򵼳�ѹ���ļ�
			if(null != exportTableBean && exportTableBean.size() > SheetNumber){
				//������Ҫ�����ı��������sheet����
				int size = exportTableBean.size();
				//���ڴ�����ɵ�excel�ļ�����  
				List<String> fileNames = new ArrayList<String>();
				//����ѹ���ļ���ȫ·��
				zipFilePath = filePath + File.separator + "cwm_" + System.currentTimeMillis() + ".zip";
				File zip = new File(zipFilePath);
				while (size - SheetNumber > 0 || size > 0) {
					fileName = "cwm_" + System.currentTimeMillis() + ".xlsx";
					//����excel��ȫ·��
					fullFilePath = filePath + File.separator + fileName;
					fileNames.add(fullFilePath);
					os = new FileOutputStream(fullFilePath);
					index = count * SheetNumber;
					//list�б����ε���excel�ļ�
					XSSFWorkbook wb = createColumnXSSF(exportTableBean,index);
					// д�ļ�
					wb.write(os);
					count++;
					size -= SheetNumber;
				}
				//��excel�ļ�����ѹ���ļ�
				File srcfile[] = new File[fileNames.size()];  
		        for (int j = 0, n1 = fileNames.size(); j < n1; j++) {  
		            srcfile[j] = new File(fileNames.get(j));  
		        }  
		        ZipFiles(srcfile, zip);  
			}else{
				fileName = "cwm_" + System.currentTimeMillis() + ".xlsx";
				zipFilePath = filePath + File.separator + fileName;
				os = new FileOutputStream(zipFilePath);
				//list�б����ε���excel�ļ�
				XSSFWorkbook wb = createColumnXSSF(exportTableBean,0);
				// д�ļ�
				wb.write(os);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			// �ر������
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
	
	
	 //ѹ���ļ�  
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
	 * ����Ŀ��·�����ļ���
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
	 * ������Ϣд��Excel
	 * @param sheet
	 * @param metadataList
	 * @return
	 */
	public XSSFWorkbook createColumnXSSF(List<ExportTableBean> exportTableBeanList,int start) {
		//����workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		//������ʽ
		XSSFCellStyle cellStyle =workbook.createCellStyle();
		cellStyle.setWrapText(true);//�Զ�����
		XSSFSheet sheet_first = workbook.createSheet("˵��");// ��һ������
		// �ϲ���Ԫ�񣨿�ʼ�У������У���ʼ�У������У�
		sheet_first.addMergedRegion(new CellRangeAddress(0, 2, 0, 4));
		Cell cell = sheet_first.createRow(0).createCell(0);
		cell.setCellValue("    һ��sheet��һ�������Ϣ����һ��sheet��˵������Ϣ�������ı���Ϣ���ݴӵڶ���sheet��ʼ��\n sheet��Ϊ����+'_'+Ŀǰ�����");
		cell.setCellStyle(cellStyle);
		int sheetCount = 1;
		String sheetName = "";
		for(int k = start;k < (start + SheetNumber);k++){
			//���list��sizeС�ڵ���k,��ֹͣ
			if(exportTableBeanList.size() <= k){
				break;
			}
			//list������ģ���k��ʼ
			ExportTableBean exportTableBean = exportTableBeanList.get(k);
			// ��ÿ��Ԫ���ݱ���һ��sheet
			//Ϊ��sheet�����ظ����Ա������н�ȡ��Excel��sheet�����ó���31λ��
			sheetName = exportTableBean.getTableName();
			if(sheetName.length() > 27){
				sheetName = sheetName.substring(0, 27);
			}
			//ÿ��excel�ļ��������255sheet
			if(sheetCount > 255){
				break;
			}
			//sheet��Ϊ����_�����
			XSSFSheet sheet = workbook.createSheet(sheetName + "_" + (sheetCount++));
			// ����̶�ֵд��
			
			sheet.autoSizeColumn(1, true);
			//index��¼��ǰ����
			int index = 0;
			// �ѱ�汾д��
			sheet.createRow(index++).createCell(1).setCellValue(exportTableBean.getVersion());
			// �ѱ���д��Excel����
			sheet.createRow(index++).createCell(1).setCellValue(exportTableBean.getTableName());
			// �ѱ���������д��
			sheet.createRow(index++).createCell(1).setCellValue(exportTableBean.getChineseName());
			
			int rowNum = sheet.getLastRowNum();
			// �ϲ���Ԫ��
			int coloumNum = sheet.getRow(sheet.getLastRowNum()).getPhysicalNumberOfCells();
			if(coloumNum >= 1){
				for (int i = 0; i <= (rowNum + 1); i++) {
					sheet.addMergedRegion(new CellRangeAddress(i,i, 1, coloumNum - 1));
				}
			}
			sheet.setDefaultColumnWidth(10);
			//cellStyle.setWrapText(true);//�Զ�����
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
