package aestec.lithosphere.strategy.erosion;

import aestec.lithosphere.Lithosphere;
import org.apache.commons.math3.distribution.CauchyDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.Random;

public class DistributionErosionStrategy implements ErosionStrategy {
    private int landcenter=325, oceancenter=-4600, gamma=675, sigma=1000;
    private double landratio;

    private static Random random = new Random(0);

    private NormalDistribution gauss;
    private CauchyDistribution lorentz;

    private int binwidth, left, binN;

    public DistributionErosionStrategy(double landratio) {
        this.landratio = landratio;
        gauss = new NormalDistribution(oceancenter,sigma);
        lorentz = new CauchyDistribution(landcenter,gamma);

        binwidth=200;
        binN=100;
        left=-10000;
    }

    @Override
    public void erode(Lithosphere l) {
        double[] target = getDistribution(), values=new double[binN],bias=new double[binN];

        //values is histogram of elevations
        for (int x=0;x<l.getXDim();x++) for (int y=0; y<l.getYDim();y++) {
            int z = l.get(x,y);
            z=Math.max(z,left);
            z=Math.min(z,left+binN*binwidth-1);
            l.set(x,y,z);

            values[(z-left)/binwidth]++;
        }
        for (int i=0; i<binN; i++) values[i]/=l.getXDim()*l.getYDim();

        //bias is difference to target, summed
        bias[0]=values[0]-target[0];
        for (int i=1; i<binN; i++) bias[i]=values[i]-target[i]+bias[i-1];

        //values is
        for (int i=1; i<binN; i++) values[i]=Math.max(Math.min(Math.abs(bias[i]/values[i]),1),0);

        for (int x=0;x<l.getXDim();x++) for (int y=0; y<l.getYDim();y++) {
            int z = l.get(x,y);
            //z+=(int)(random.nextDouble()*200*bias[(z-left)/binwidth]);
            if (random.nextDouble()<values[(z-left)/binwidth]) z+=Math.signum(bias[(z-left)/binwidth])*binwidth;
            l.set(x,y,z);
        }
    }

    private double cumulativeProbability(int z1, int z2) {
        return landratio*(gauss.cumulativeProbability(z2)-gauss.cumulativeProbability(z1))
                +(1-landratio)*(lorentz.cumulativeProbability(z2)-lorentz.cumulativeProbability(z1));
    }

    private double[] getDistribution() {


        double[] dist = new double[binN];
        double sum=0;
        for (int i=0; i<binN;i++) {
            dist[i]=cumulativeProbability(left+binwidth*i,left+binwidth*(i+1));
            sum+=dist[i];
        }
        for (int i=0; i<binN;i++) {
            dist[i]/=sum;
        }
        return dist;
    }


}
