
import java.util.ArrayList;
import java.util.Scanner;

public class MutualFundImpl implements MutualFundNAV {

    public MutualFundImpl() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        System.out.println("Mutual Funds");


        MutualFundImpl mutualFundImpl = new MutualFundImpl();
        ArrayList<Integer> list = mutualFundImpl.getInput();
        int periodOfInvestment = list.get(0);
        int horizon = list.get(1);
        new MutualFundProcessor().process(periodOfInvestment, horizon);
    }

    public void display() {
        // TODO Auto-generated method stub

    }

    public ArrayList<Integer> getInput() {
        ArrayList<Integer> periodAndHorizon = new ArrayList<Integer>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Period of Investment: ");
        int periodOfInvestment = sc.nextInt();
        System.out.print("Horizon: ");
        int horizon = sc.nextInt();
        periodAndHorizon.add(periodOfInvestment);
        periodAndHorizon.add(horizon);
        sc.close();
        return periodAndHorizon;
    }


}
