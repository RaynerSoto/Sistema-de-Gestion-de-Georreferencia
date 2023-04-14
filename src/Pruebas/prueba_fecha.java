package Pruebas;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class prueba_fecha {

	@Test
	public void test() {
		try {
			Date calendario = new Date(2022,02,39);
			System.out.print(calendario.getDay());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
