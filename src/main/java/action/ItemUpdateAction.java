package action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.ItemDao;
import model.Item;

public class ItemUpdateAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("edit");
		var mapper = new ObjectMapper();

		try {
			var dao = new ItemDao();
			var item = id != null ? dao.searchItemById(Integer.parseInt(id)) : new Item();
			var category = dao.getCategoryList();
			var option = dao.getAllOptionsGroupedByCategory();
			String optionJson = mapper.writeValueAsString(option);

			request.setAttribute("item", item);
			request.setAttribute("categoryList", category);
			request.setAttribute("optionList", optionJson);

		} catch (Exception e) {
			// デバッグ用のスタックトレース.
			e.printStackTrace();

			// フロントエンド用のメッセージ.
			request.setAttribute("errMsg", e.getMessage());
		}
		return "/admin/modify_update.jsp";
	}

}
