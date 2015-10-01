package ifis.skysim2.experiments;

import ifis.skysim2.algorithms.*;
import ifis.skysim2.algorithms.subspaceAware.SkylineAlgorithmParallelBNLLinkedListLazySync_SubspaceAware;
import ifis.skysim2.algorithms.subspaceAware.CreateCombSet;
import ifis.skysim2.algorithms.subspaceAware.SubspaceHelper;
import ifis.skysim2.data.generator.*;
import ifis.skysim2.data.sources.PointSource;
import ifis.skysim2.data.sources.PointSourceDisk;
import ifis.skysim2.data.sources.PointSourceRAM;
import ifis.skysim2.simulator.DataSource;
import ifis.skysim2.simulator.SimulatorConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TestSubspaceSkyline {

    /**
     * Creates a simple config setting loading the cars file with ID's, and
     * using a subspace-aware Skyline algorithm
     *
     * @return
     */
    public static SimulatorConfiguration getSimpleConfig() {
        System.out.println("enter the getSimpleConfig method in the TestSubspaceSkyline");
        SimulatorConfiguration config = new SimulatorConfiguration();
        // well, this is weird. Due to legacy reasons, loading a CSV file is indeed 
        // a memory source which loads from a file.... don't think about this, thats 
        // just the way it is... 
        // The Datasource.FILE will read a serialized binary file, thats something else.... 

        config.setDataSource(DataSource.MEMORY);

        //  use this for synthetic data
        /**
         * config.setUseDefaultGeneratorSeed(true); config.setD(4);
         * config.setN(8); config.setNumLevels(3); DataGenerator gen = new
         * DataGeneratorIndependent();
         */
        DataGeneratorFromCSVFile gen = new DataGeneratorFromCSVFile("./data/cars_ID.csv");
        config.setD(gen.getD());
        config.setN(gen.getN());
        System.out.println("the number of dataGeneratorFromCSVFile d = " + gen.getD()+" n = "+gen.getN() );

        config.setDataGenerator(gen);
 
        //      ** choose an algorithm
        config.setSkylineAlgorithm(new SkylineAlgorithmParallelBNLLinkedListLazySync_SubspaceAware());
      // config.setSkylineAlgorithm(new SkylineAlgorithmKdTrie(false));
        // config.setSkylineAlgorithm(new SkylineAlgorithmBNL());
        //config.setSkylineAlgorithm(new SkylineAlgorithmPskyline());
        config.getSkylineAlgorithm().setExperimentConfig(config);
        // its indeed a multi-cpu algorithm....
        config.setNumberOfCPUs(4);
        config.setDistributedNumBlocks(100);
        config.setStaticArrayWindowSize(200000);
        config.setDeleteDuringCleaning(true);
        
        System.out.println("out the getSimpleConfig method in the TestSubspaceSkyline");
        
        return config;
    }
    /**
     * This prepares the data sets, e.g. generating them or loading them from a
     * file.
     *
     * @param config
     * @return
     */
    public static PointSource getData(SimulatorConfiguration config) {
        if (config == null) {
            throw new IllegalArgumentException("No configuration set.");
        }

        DataGenerator dg = config.getDataGenerator();
        if (config.isUseDefaultGeneratorSeed()) {
            dg.resetToDefaultSeed();
        }

        int d = config.getD();
        int n = config.getN();
        int[] numLevels = config.getNumLevels();
        DataSource ds = config.getDataSource();
        File df = config.getDataFile();
        int bytesPerRecord = config.getBytesPerRecord();

        long tStart, tStop;

        float[] data = null;
        tStart = System.nanoTime();
        if (ds == DataSource.MEMORY) {
            data = dg.generate(d, n, numLevels);
        } else {
            try {
                if (ds == DataSource.FILE) {
                    dg.generate(d, n, numLevels, df, bytesPerRecord);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        tStop = System.nanoTime();

        PointSource pointSource;
        if (ds == DataSource.MEMORY) {
            pointSource = new PointSourceRAM(d, data);
        } else {
            pointSource = new PointSourceDisk(df, bytesPerRecord, n, d);
        }

        // Clean memory (for measuring memory consumption)
        return pointSource;
    }
    /**
     * Reade a set of objects from a file to match
     * @param fileName
     * @return 
     */
    private static int[] readExistObject(String fileName){
		SimpleFileReader sp = new SimpleFileReader(fileName);
		String s=sp.readNextLine();
		String[] exList=s.split(" ");
		int[] res=new int[exList.length];
		for(int i=0;i<exList.length;i++){
			res[i]=Integer.parseInt(exList[i]);
		}
		for(int i=res.length-1;i>0;i--){
			int k=0;
			for(int j=0;j<=i;j++){
				if(res[j]>res[k]){
					k=j;
				}
			}
			int temp=res[k];
			res[k]=res[i];
			res[i]=temp;
		}
		return res;
	}
    /**
     * Get the number of same integer from array a and b
     * @param a
     * @param b
     * @return 
     */
    private static int getNumOfSamObs(int[] a,int[] b){
	int res=0;
	if(a==null||b==null){
            return res;
	}
	for(int i=0,j=0;i<a.length&&j<b.length;){
            if(a[i]==b[j]){
		res++;
		i++;
		j++;
            }else if(a[i]>b[j]){
		j++;
            }else{
		i++;
            }
        }
	return res;
    }
    /**
     * Match the selected skyline objects with the exists
     * @param combs
     * @param skylineObjects
     * @param existObjects 
     */
    private static void match(int[][] combs,int[][] skylineObjects,int[] existObjects){
	double[] re1=new double[combs.length];
	double[] re2=new double[combs.length];
	int[] matchNum=new int[combs.length];
	int sele1=0,sele2=0;
	re1[0]=re2[0]=0.0;
	for(int i=0;i<re1.length;i++){
            matchNum[i]=getNumOfSamObs(existObjects,skylineObjects[i]);
            re1[i]=matchNum[i]*1.0/existObjects.length;
            if(combs[i]!=null){
		re2[i]=matchNum[i]*1.0/skylineObjects[i].length;
            }else{
            	re2[i]=0;
            }
            if(re1[i]>re1[sele1]){
		sele1=i;
            }
            if(re1[i]>re2[sele2]){
		sele2=i;
            }
	}
        outPutResult("./data/result.txt",existObjects,combs,skylineObjects,matchNum,re1,re2,sele1,sele2);
    }
    /**
     * Output the result to a file
     * @param fileName
     * @param existObjects
     * @param combsOfAtt
     * @param objectsOfCombs
     * @param matchNum
     * @param re1
     * @param re2
     * @param sele1
     * @param sele2 
     */
    public static void outPutResult(String fileName,int[] existObjects,int[][] combsOfAtt,int[][] objectsOfCombs,int[] matchNum,double[] re1,double[] re2,int sele1,int sele2){
	SimpleFileWriter sw = new SimpleFileWriter(fileName);
	String s="";
		for(int i=0;i<existObjects.length;i++){
			s=s+existObjects[i]+" ";
		}
		for(int i=0;i<combsOfAtt.length;i++){
                    sw.appendToNextLine((1+i)+">>");
			sw.appendToNextLine("Exist Objects: "+s);
                        sw.appendToNextLine("the number of exist objects: "+existObjects.length);
			String temp="";
			for(int j=0;objectsOfCombs[i]!=null&&j<objectsOfCombs[i].length;j++){
				temp=temp+objectsOfCombs[i][j]+" ";
			}
			sw.appendToNextLine("Select Objects: "+temp);
                        sw.appendToNextLine("the number of select objects: "+objectsOfCombs[i].length);
			temp="";
			for(int j=0;j<combsOfAtt[i].length;j++){
				temp=temp+combsOfAtt[i][j]+" ";
			}
			sw.appendToNextLine("Select Atts: "+temp);
			sw.appendToNextLine("The number of match objects: "+matchNum[i]);
			sw.appendToNextLine("result 1: "+re1[i]);
			sw.appendToNextLine("result 2: "+re2[i]);
			sw.appendToNextLine("--------------------------");
		}
		sw.appendToNextLine(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		sw.appendToNextLine((sele1+1)+">>");
		sw.appendToNextLine("Exist Objects: "+s);
                sw.appendToNextLine("the number of exist objects: "+existObjects.length);
		String temp="";
		for(int j=0;objectsOfCombs[sele1]!=null&&j<objectsOfCombs[sele1].length;j++){
			temp=temp+objectsOfCombs[sele1][j]+" ";
		}
		sw.appendToNextLine("Select Objects: "+temp);
                sw.appendToNextLine("the number of select objects: "+objectsOfCombs[sele1].length);
		temp="";
		for(int j=0;j<combsOfAtt[sele1].length;j++){
			temp=temp+combsOfAtt[sele1][j]+" ";
		}
		sw.appendToNextLine("Select Atts: "+temp);
		sw.appendToNextLine("The number of match objects: "+matchNum[sele1]);
		sw.appendToNextLine("result 1: "+re1[sele1]);
		sw.appendToNextLine("result 2: "+re2[sele1]);
		sw.appendToNextLine(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		sw.appendToNextLine((sele2+1)+">>");
		sw.appendToNextLine("Exist Objects: "+s);
                sw.appendToNextLine("the number of exist objects: "+existObjects.length);
		for(int j=0;objectsOfCombs[sele2]!=null&&j<objectsOfCombs[sele2].length;j++){
			temp=temp+objectsOfCombs[sele2][j]+" ";
		}
		sw.appendToNextLine("Select Objects: "+temp);
                sw.appendToNextLine("the number of select objects: "+objectsOfCombs[sele2].length);
		temp="";
		for(int j=0;j<combsOfAtt[sele2].length;j++){
			temp=temp+combsOfAtt[sele2][j]+" ";
		}
		sw.appendToNextLine("Select Atts: "+temp);
		sw.appendToNextLine("The number of match objects: "+matchNum[sele2]);
		sw.appendToNextLine("result 1: "+re1[sele2]);
		sw.appendToNextLine("result 2: "+re2[sele2]);
		sw.appendToNextLine(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		sw.close();
	}
    /**
     * Simply run a skyline algorithm from a csv file.
     *
     * @param args
     */
    public static void main(String[] args) {
        SimulatorConfiguration config = getSimpleConfig();
        PointSource data = getData(config);
        SkylineAlgorithm alg = config.getSkylineAlgorithm();
        alg.setExperimentConfig(config);
        // subspaces are simply represented as arrays
        // this next code will get all subspaces, and then remove the dimension 0 which contains the record ID (and which is therefore very bad for skyline computaion)
        int[] subspaces = SubspaceHelper.getFullSubspace(config);        
        subspaces = SubspaceHelper.removeDimension(subspaces, 0);
        
        int[][] combs = (new CreateCombSet(subspaces)).getRes();
        int[][] skyObjects = new int[combs.length][];
        for(int i=0;i<combs.length;i++){
            if(combs[i].length>0){
                config.setSubspaceIndexes(combs[i]);
                List<float[]> skyline = alg.compute(data);
                skyObjects[i]=new int[skyline.size()];
                for(int j=0;j<skyline.size();j++){
                    skyObjects[i][j]=(int)skyline.get(j)[0];
                }
            }else{
                skyObjects[i]=new int[0];
            }
        }
        for(int k=0;k<skyObjects.length&&skyObjects[k].length>0;k++){
            for(int i=skyObjects[k].length-1;i>0;i--){
                int index=0;
                for(int j=0;j<=i;j++){
                    if(skyObjects[k][index]<skyObjects[k][j]){
                        index=j;
                    }
                    int temp=skyObjects[k][i];
                    skyObjects[k][i]=skyObjects[k][index];
                    skyObjects[k][index]=temp;
                }
            }
        }
        int[] existObjects = readExistObject("./data/existObjects.txt");
        match(combs,skyObjects,existObjects);
        // run
        // PointSource2CSV.writeToCSVFile(data, new File("./data/memoryDump.csv"));
        System.out.println("Skyline Algorithm: " + config.getSkylineAlgorithm().toString());
        //System.out.println("Skyline size: " + skyline.size());

        //
    }

  

}
