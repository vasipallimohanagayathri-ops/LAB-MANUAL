class Pair{
    String stationName;
    int time;
    int distance;
    int noofroutes;
    Pair(String stationName,int time){
        this.stationName=stationName;
        this.time=time;
    }
    Pair(int distance,int noofroutes){
        this.distance=distance;
        this.noofroutes=noofroutes;
    }
    
}
class UndergroundSystem {
    HashMap<String,Pair> travel;
    HashMap<Integer,Pair> checkin;
    public UndergroundSystem() {
        travel=new HashMap<>();
        checkin=new HashMap<>();
    }
    
    public void checkIn(int id, String stationName, int t) {
        checkin.put(id,new Pair(stationName,t));
    }
    
    public void checkOut(int id, String stationName, int t) {
        String startStationName=checkin.get(id).stationName;
        int arrivaltime=checkin.get(id).time;
        int totaltimetravelled=t-arrivaltime;
        String route=startStationName+"-"+stationName;
        if(!travel.containsKey(route)){
            travel.put(route,new Pair(totaltimetravelled,1));
        }
        else{
            int dist=travel.get(route).distance;
            int noofroutes=travel.get(route).noofroutes;
            travel.put(route,new Pair(dist+totaltimetravelled,noofroutes+1));
        }
    }
    
    public double getAverageTime(String startStation, String endStation) {
        String route=startStation+"-"+endStation;
        double avg=0.0;
        if(!travel.containsKey(route)){
            return avg;
        }
        else{
            avg=travel.get(route).distance/(double)travel.get(route).noofroutes;
        }
        return avg;
    }
}
