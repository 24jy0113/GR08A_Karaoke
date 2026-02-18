package test;

import dao.RoomDao;

public class RoomDaoTest {

	public static void main(String[] args) {
		try {
		System.out.println(RoomDao.getRoomByRoomNumber(101));
		}catch (Exception e) {
			// TODO: handle exception
		}

	}

}
