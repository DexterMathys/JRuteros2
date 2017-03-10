package main;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.imp.RouteDaoImp;
import com.imp.TravelDaoImp;
import com.model.Activity;
import com.model.Apoint;
import com.model.Difficulty;
import com.model.Photo;
import com.model.Route;
import com.model.Routescore;
import com.model.Travel;
import com.model.User;
import com.model.UserRoute;

public class Test {

	 public static void main(String[] args) {
	        System.out.println(Difficulty.EASY);
	       /* Route route = new Route();
	        route.setName("Ruta de prueba");
	        route.setDifficulty(Difficulty.EASY);
	        
	        TravelDaoImp travelDAO = new TravelDaoImp();
	        List<Travel> listaTravel = travelDAO.listar();
	        
	        route.setTravel(listaTravel.get(1));*/
	        
	        RouteDaoImp routeDAO = new RouteDaoImp();
	        //routeDAO.nuevo(route);
	        
	        for (Route route : routeDAO.listar()) {
	            // fruit is an element of the `fruits` array.
	        	System.out.println(route.getDifficulty());
	        }
	        Route route2;
	        route2 = routeDAO.obtener(new Long(3));
	        if (route2 != null){
	        	//System.out.println(route2.getTravel().getApoints());
	        	 for (Iterator<Apoint> it = route2.getTravel().getApoints().iterator(); it.hasNext(); ) {
	        	        Apoint f = it.next();
	        	        
	        	            System.out.println(f.getLatitude());
	        	    }
	        }
	        
	        System.out.println(Difficulty.values());
	        
	 }

}

/*public Route(Travel travel, User user, Activity activity, Date date, String description, Difficulty difficulty,
		float distance, boolean isCircular, boolean isPublic, String name, Date time, Set<Photo> photos,
		Set<UserRoute> userRoutes, Set<Routescore> routescores) */