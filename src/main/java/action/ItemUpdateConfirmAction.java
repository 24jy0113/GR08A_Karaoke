package action;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.ItemDao;
import model.Item;
import model.Option;

public class ItemUpdateConfirmAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		// セッションの取得（なければ新規作成、あれば既存ものを返す）.
		HttpSession session = request.getSession();

		// セッションに編集中の商品オブジェクトを格納する.
		Item item = (Item) (session.getAttribute("editItem"));

		// パラメータの受け取り.
		String name = request.getParameter("name");
		int price = Integer.parseInt(request.getParameter("price"));
		int orderNumber = Integer.parseInt(request.getParameter("order_number"));
		int categoryId = Integer.parseInt(request.getParameter("category"));
		boolean stock = Boolean.parseBoolean(request.getParameter("stock"));
		String[] optionIdsStr = request.getParameterValues("option");
		List<Option> optionList = new ArrayList<>();

		try {
			var dao = new ItemDao();

			// オプションの変換.
			if (optionIdsStr != null) {
				List<Integer> optionIdList = new ArrayList<>();
				optionIdList = Arrays.stream(optionIdsStr)
						.map(Integer::parseInt)
						.collect(Collectors.toList());

				optionList = dao.searchOptionByOptionIdList(optionIdList);
			}

			// カテゴリー名の取得.
			String categoryName = dao.getCategoryList().get(categoryId);

			// 商品オブジェクトに受け取ったパラメータを入れる.
			item.setName(name);
			item.setPrice(price);
			item.setItemNo(orderNumber);
			item.setCategoryId(categoryId);
			item.setCategory(categoryName);
			item.setStock(stock);
			item.setOptionList(optionList);

			// ファイルの受け取り.
			var imagePart = request.getPart("image");

			// ファイルがある場合.
			if (imagePart != null && imagePart.getSize() != 0) {
				// ファイル名を取得する.
				String fileName = imagePart.getSubmittedFileName();

				// 物理保存用のパス（これはフルパスが必要）.
				String uploadPath = request.getServletContext().getRealPath("/img/items");

				// 物理保存実行
				imagePart.write(uploadPath + File.separator + fileName);
				
				// 商品オブジェクトに新しいファイル名を入れる.
				item.setImage(fileName);
			}

		} catch (Exception e) {
			// デバッグ用のスタックトレース.
			e.printStackTrace();

			// フロントエンド用のメッセージ.
			request.setAttribute("errMsg", e.getMessage());
		}

		return "/admin/modify_update_confirm.jsp";
	}

}
