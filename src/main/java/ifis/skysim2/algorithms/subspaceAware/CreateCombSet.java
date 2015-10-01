/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifis.skysim2.algorithms.subspaceAware;

/**
 *
 * @author fish
 */
public class CreateCombSet {
    private int[][] res=null;
    private int n;
    private boolean[] flag;
    private int[] num;
    private int index=0;
    /**
     * please make sure that the integers in the array num[] are unique
     * this class help you get all the possible combinations of elements in num[]
     * @param num 
     */
    public CreateCombSet(int[] num){
        if(num==null){
            throw new IllegalArgumentException();
        }
        n=num.length;
        res = new int[(int)(Math.pow(2, n))][];
        this.num=num;
        flag = new boolean[num.length];
        create(0);
    }
    public void create(int x){
        if(this.n<=x){
            int count=0;
            for(int i=0;i<flag.length;i++){
                if(flag[i]){
                    count++;
                }
            }
            res[index]=new int[count];
            for(int i=0,j=0;i<flag.length;i++){
                if(flag[i]){
                    res[index][j++]=num[i];
                }
            }
            index++;
            return;
        }
        flag[x]=true;
        create(x+1);
        flag[x]=false;
        create(x+1);
    }
    public int[][] getRes(){
        return res;
    }
    /*
    public static void main(String[] args){
        int[] test = new int[5];
        for(int i=0;i<test.length;i++){
            test[i]=i+1;
        }
        CreateCombSet combs = new CreateCombSet(test);
        int[][] re = combs.getRes();
        System.out.println(re.length+">>");
        for(int i=0;i<re.length;i++){
            System.out.print(re[i].length+">>");
            for(int j=0;re[i]!=null&&j<re[i].length;j++){
                
                System.out.print(re[i][j]+" ");
            }
            System.out.println();
        }
    }
    */
}
