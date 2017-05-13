import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


public class PutWallTest {
	
	int Horizontal[] = new int[]{0,1,2,3,4,5,6,7,16,17,18,19,20,21,22,23,32,33,34,35,36,37,38,39,48,49,50,51,52,53,54,55,64,65,66,67,68,69,70,71,80,81,82,83,84,85,86,87,96,97,98,99,100,101,102,103,112,113,114,115,116,117,118,119};
	int Vertical[] = new int[]{8,9,10,11,12,13,14,15,24,25,26,27,28,29,30,31,40,41,42,43,44,45,46,47,56,57,58,59,60,61,62,63,72,73,74,75,76,77,78,79,88,89,90,91,92,93,94,95,104,105,106,107,108,109,110,111,120,121,122,123,124,125,126,127};
	int index;
	
	PutWallTest(int index){
		this.index = index;
	
	}
	
    @Before
	public void setUp() throws Exception {
    	
	}
	
	@Test
	public void test() {
		System.out.println("test");
		}
		}
		
		
		@RunWith(Parameterized.class)
		public class ExhaustiveResearchTest {

			@Parameters(name = "(isRed = {0}, x = {1}, y = {2})")
			public static Collection<Object[]> data() {

				List<Object[]> l = new ArrayList<>();

				for (int t = 0; t < 2; t++) {
					for (int y = 0; y < 8; y++) {
						for (int x = 0; x < 8; x++) {
							l.add(new Object[] { t == 0, x * 2, y * 2 });
						}
					}
				}

				return l;
			}
	}

}
