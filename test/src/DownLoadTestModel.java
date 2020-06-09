import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.baosight.web.struts.BActionContext;

public class DownLoadTestModel {

	private String downloadTesr(ActionContext context) {
		ServletOutputStream out = null;
		HSSFWorkbook wb = new HSSFWorkbook();
		HttpServletResponse response = context.getResponse();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
		String filename = "Stock" + df.format(new Date()) + ".xls";
		response.setContentType("application/multipart");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ filename);
		out = response.getOutputStream();
		wb.write(out);
		return null;
	}

}
