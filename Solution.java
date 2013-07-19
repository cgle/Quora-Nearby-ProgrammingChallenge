/* Solution to Quora's Nearby Programming Challenge
 * Class: Solution Solution to Quora's Nearby
 *
 * ****************************
 * Parameters Related to TOPICS
 * ****************************
 * @param T Number of Topics
 * @param Q Number of Questions
 * @param N Number of Queries
 * @param AllTopics Integer array of size T to store all Topic Id's
 * @param TopCoord double array of size T x 2 to store Coordinates of all Topics
 * @param suffix double array of size T to store Indexes of Topic Id's in Special Form (for later use)
 * @param countQuestionForEachTopicId  Stores the Number of Questions associated with Each Topic ID
 * @param QuestionsAssociatedWithEachTopicId Stores the Question ID's for a Particular Topic in an integer Array
 * @param QuestionsAssociatedWithEachTopicIdList Stores the Questions ID's for a Particular Topic in an ArrayList
 * @param mapTopicToQues A HashMap which maps from Specific TopicId to Array of Question Id's 
 *                       (Question Id's which are associated with that Topic) 
 * @param mapTopicToNumQues A HashMap which maps from Specific TopicId to NumberOfQuestions Associated with that Topic Id
 * @param mapTopicToQuesIDsList A HashMap which maps from Specific TopicId to ArrayList of Question Id's 
 *                       (Question Id's which are associated with that Topic)
 * 
 * *******************************
 * Parameters Related to QUESTIONS
 * *******************************
 * @param AllQuest Integer array to Store all Question Id's
 * @param NumOfTopic Integer array to Store Number of Topics Associated with that Question
 * @param TopicIds A 2-dimensional array of type integers to store Topic Id's of 
 *                 all Topics that are associated with that question    
 * @param NumOfUselessQues Stores the Number Of Questions that are not supposed to be Outputted
 * @param ActualNumOfReturnableQuestions At a particular instant, it stores the actual number of questions to return
 * 
 * *****************************
 * Parameters Related to QUERIES
 * *****************************
 * @param QueryRes A character array to store returnable types('q' or 't') of all queries
 * @param NumRes A 1-D Integer array to store Number of Results to return
 * @param QCoord A 2-D array to store Coordinates of Query.
 * @param ResultTopicIds A 2-D which holds the results to return
 * @param out for output use only
 * 
 * ************
 * Author Info:
 * ************
 * @author Gurpreet Singh
 * http://gurpreetsinghny.com/
 * https://github.com/harry1357931?tab=repositories
 * Email: harry1357931@gmail.com (preferred)
 * Cellphone: 347-653-0056
 * 
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Solution {
	
    public static MemoryMXBean memoryBean;
    public static int T,Q,N;
    // All Topics
    public static int[] AllTopics;                                // All Topic Ids 
	public static double[][] TopCoord;                           // All Topic Coords
	public static double[] suffix;
	public static int[] countQuestionForEachTopicId;            // Num of Questions Associated With Each Topic Id    
	public static int[][] QuestionsAssociatedWithEachTopicId;  // Question Ids related to Specific Topic
	public static ArrayList<Integer>[] QuestionsAssociatedWithEachTopicIdList;
	public static HashMap<Integer, int[]> mapTopicToQues = new HashMap<Integer, int[]>();
	public static HashMap<Integer, Integer> mapTopicToNumQues = new HashMap<Integer, Integer>();
	public static HashMap<Integer, ArrayList<Integer>> mapTopicToQuesIDsList = new HashMap<Integer, ArrayList<Integer>>();
	// AllQuestions
	public static int[] AllQuest;         // All Question Id's
	public static int[] NumOfTopic;      // Number of Topics
	public static int[][] TopicIds;     // Id's of all Topics that Question is related to...
	public static int NumOfUselessQues;
	public static int ActualNumOfReturnableQuestions;
	// All Queries
	public static char[] QueryRes;       // Either t or q...
	public static int[] NumRes;         // Number of Results to Return
	public static double[][] QCoord;   // Location from where Query is made 
	// Query Results
	public static int[][] ResultTopicIds;     // Need to change name to make more sense
	// Used for Output
    public static BufferedWriter out; 
    static { 
    	try {    
    	  out = new BufferedWriter(new OutputStreamWriter(System.out));
    	}
    	catch (Exception e) {
    	  e.printStackTrace();
    	}
    }
     
	public static void main(String args[] ) throws Exception {
		memoryBean = ManagementFactory.getMemoryMXBean();	
    	
	    BufferedReader tfi = new BufferedReader(new InputStreamReader(System.in));
    	
    	//TextFileInput tfi = new TextFileInput("case5.txt");    
    	StringTokenizer myTokens= new StringTokenizer(tfi.readLine(), " "); 
    	
    	 // Parameters Instantiation
    	 T = Integer.parseInt(myTokens.nextToken()); 
    	 final long startTime = System.currentTimeMillis();
    	 Q = Integer.parseInt(myTokens.nextToken()); 
		 N = Integer.parseInt(myTokens.nextToken());
    	                                         
    	 // All Topics Instantiation
		 AllTopics = new int[T]; 
    	 TopCoord = new double[T][2];
         suffix = new double[T];                // e.g: of entry: "0.0000"+AllTopics[i]+"5"
         QuestionsAssociatedWithEachTopicIdList = new ArrayList[T];
         countQuestionForEachTopicId = new int[T];
         
    	 for(int i=0; i < T; i++) {
    		myTokens= new StringTokenizer(tfi.readLine(), " ");
    		AllTopics[i] =  Integer.parseInt(myTokens.nextToken());
            TopCoord[i][0] = Double.parseDouble(myTokens.nextToken());
            TopCoord[i][1] = Double.parseDouble(myTokens.nextToken());
            
            QuestionsAssociatedWithEachTopicIdList[i] = new ArrayList<Integer>();
            mapTopicToQuesIDsList.put(AllTopics[i], QuestionsAssociatedWithEachTopicIdList[i]);
            mapTopicToNumQues.put(AllTopics[i], countQuestionForEachTopicId[i]);
            
            if(i<10)
                suffix[i] = Double.parseDouble("1.0000000"+i+"5");        // change back to 0
            else if(i<100)
                suffix[i] = Double.parseDouble("1.000000"+i+"5");
            else if(i<1000)
                suffix[i] = Double.parseDouble("1.00000"+i+"5");
            else
                suffix[i] = Double.parseDouble("1.0000"+i+"5");
    	 }
    	
    	QuestionsAssociatedWithEachTopicId = new int[T][];    // new int[T][1000];
    	
    	// All Questions Instantiation
    	AllQuest = new int[Q];          // All Question Id's
    	NumOfTopic = new int[Q];       // Number of Topics
    	TopicIds = new int[Q][];      // Id's of all Topics that Question is related to...
    	NumOfUselessQues = 0;
    	for(int i=0; i < Q; i++) {
    		myTokens= new StringTokenizer(tfi.readLine(), " ");
            AllQuest[i] =  Integer.parseInt(myTokens.nextToken());
            NumOfTopic[i] = Integer.parseInt(myTokens.nextToken());
            TopicIds[i] = new int[NumOfTopic[i]];
            if(NumOfTopic[i] == 0){
            	NumOfUselessQues++;
            	continue;
            }
            for(int j=0; j < NumOfTopic[i]; j++){
            	TopicIds[i][j] =  Integer.parseInt(myTokens.nextToken());
            	mapTopicToQuesIDsList.get(TopicIds[i][j]).add(AllQuest[i]);
            	mapTopicToNumQues.put(TopicIds[i][j], mapTopicToNumQues.get(TopicIds[i][j]) + 1);	
    		}
    	}
    	
    	int size;
    	for(int i=0; i < T; i++){
    		size = mapTopicToQuesIDsList.get(AllTopics[i]).size();
    		QuestionsAssociatedWithEachTopicId[i] = new int[size];
    		for(int j=0; j < size; j++ ){
    			QuestionsAssociatedWithEachTopicId[i][j] = mapTopicToQuesIDsList.get(AllTopics[i]).get(j); 
    		}
    		mapTopicToQues.put(AllTopics[i], QuestionsAssociatedWithEachTopicId[i]);
    	}
    	
    	ActualNumOfReturnableQuestions = Q - NumOfUselessQues;
    	
    	// All Query Instantiation
    	QueryRes = new char[N];     // Either T or Q...
    	NumRes = new int[N];        // Number of Results...
    	QCoord = new double[N][2];
    	
    	for(int i=0; i < N; i++) {
    		myTokens= new StringTokenizer(tfi.readLine(), " ");
    		QueryRes[i] =   myTokens.nextToken().charAt(0);
            NumRes[i]   = Integer.parseInt(myTokens.nextToken());
            QCoord[i][0] = Double.parseDouble(myTokens.nextToken());
            QCoord[i][1] = Double.parseDouble(myTokens.nextToken());
    	}
    	
    	ResultTopicIds = new int[N][];       // 5 MB Space....10000 x 100
        
    	// Processes all Query's and Save Results in ResultTopicIds 
    	startFromto(0, N);       // 30 + 5 = 35 MB 
    	// Print Output
    	PrintOutput();
    	/*
    	double[] distances, distances2;
    	distances = new double[T];
    	distances2 = new double[T];
    	int h=4;
    	ResultTopicIds[h] = CallMainQueryProcessing(h, distances, distances2 );
    	
    	for(int j=0; j<ResultTopicIds[h].length; j++){
    		System.out.print(ResultTopicIds[h][j]+" ");
    	}
    	*/
    	
         /*
    	 T = 6000;
    	 NumRes[0] = 100; 		
    	 int TwoPercentIndex, TenPercentIndex;
    	 TwoPercentIndex = 4*NumRes[0] + (int)(0.02*(T-4*NumRes[0]));
    	 TenPercentIndex = 4*NumRes[0] + (int)(0.1*(T-4*NumRes[0]));;
    	 out.write("\nTwoPercent: " + TwoPercentIndex);
    	 out.write("\nTenPercent: " + TenPercentIndex+"\n");
         */
    	
    	/*
    	memoryBean = ManagementFactory.getMemoryMXBean();
    	final long endTime = System.currentTimeMillis();
    	out.write("Execution Time: "+ (endTime - startTime) + " ms");
    	out.newLine();
    	*/
    	out.flush();
    	/*
    	long heapMemUsed = memoryBean.getHeapMemoryUsage().getUsed();
    	long otherMemUsed = memoryBean.getNonHeapMemoryUsage().getUsed();
    	long totalMemoryUsed = heapMemUsed + otherMemUsed;
    	out.write("Total Memory used: " + totalMemoryUsed/1000000 + " MB");
    	out.newLine();
    	out.write("Heap Memory used: " + heapMemUsed/1000000 + " MB");
    	*/
    	out.close();
    	
	} // main ends here...
	
	/* NumExistsInArray(int[], int, int)
	 * Checks Whether Number 'NumToBeChecked' exists in
	 * 'printed' array or Not upto Index 'UpToIndex'
	 *  Specifically used in When Result type is 'q'
	 *  @param printed   Array in which function checks the existence of a 
	 *                   particular number...in this function it is param 'NumberToBeChecked' 
	 *  @param NumberToBeChecked  This is the number function checks whether it exists in
	 *                            'printed' array or not
	 *  @param UpToIndex  Checks upto this index for 'NumberToBeChecked' in array 'printed'
	 */
	public static boolean NumExistsInArray(int[] printed ,int NumToBeChecked, int UpToIndex){
    	if(UpToIndex == 0)
    		return false;
    	
    	for(int i=0; i< UpToIndex; i++)
    		if(printed[i]== NumToBeChecked)
    			  return true;
    	
    	return false;
   } 
   
   /* Function: startFromto
    * This function PROCESSES QUERIES from 'from' to 'to'
    * @param from beginning index of query
    * @param to  ending index of query 
    * @param distances Stores the distance of Particular Query to All Topics
    * @param distances2 used as helper array to retrieve Index of topic Ids after being sorted
    * Note that: parameter 'T' represents Total Number of Topics
    * **********************
    * Algorithm for 't' type
    * **********************
    *       pending to write here...
    * **********************
    * Algorithm for 'q' type
    * **********************
    *       pending to write here...
    */	
			
   public static void startFromto(int from, int to){
	   
	double[] distances = new double[T];
	double[] distances2 = new double[T];      
   	double x_minus = 0, y_minus = 0;
   	int IndexOfTopicIdd, NumberOfResults, DistancesCount;
   	boolean flag_1, flag_2, flag_3;
   	for(int i= from; i < to; i++){                // for each query
   		flag_1 = false; flag_2 = false; flag_3 = false;
	    DistancesCount = 0;
           
	       if(QueryRes[i] == 'q' && T <= 8000)   
	           flag_3 = true; 
           flag_3 = false;            //remove
		   if(T <= 5*NumRes[i] || NumRes[i]== 0 || flag_3){          //5* 
	    	    DistancesCount = T;
		    	for(int j=0; j < T; j++){     // going through all topics in Single Query
			    	x_minus = TopCoord[j][0]- QCoord[i][0];
		   			y_minus = TopCoord[j][1]- QCoord[i][1];
		   			distances[j] = Math.sqrt(x_minus*x_minus + y_minus*y_minus);; 
		   			distances2[j] = distances[j] + suffix[j];       // good 120 ms
		        }
	       }else{
	    	  flag_1 = true; 
	    	  double greatest = 1500000; 
	    	  int TwoPercentIndex, TenPercentIndex;   // 'greatest' at NumRes[i]th Index
	    	  boolean CheckFlag = false;
	    	  TwoPercentIndex = 5*NumRes[i] + (int)(0.02*(T-5*NumRes[i])); 
	    	  TenPercentIndex = 5*NumRes[i] + (int)(0.1*(T-5*NumRes[i]));
	    	   
              for(int j=0; j < T; j++) {                // going through all topics in Single Query
		    	 x_minus = TopCoord[j][0]- QCoord[i][0];
	   			 y_minus = TopCoord[j][1]- QCoord[i][1];
	   			 
	   			 // To Decide Whether distances[i] is a good candidate or Not
	   			 if(CheckFlag == true){
	   				 
	   				 if(Math.abs(x_minus) > greatest || Math.abs(y_minus) > greatest)
	   					 continue;
	   				 else if(Math.sqrt(x_minus*x_minus + y_minus*y_minus) > greatest)
	   					 continue;
	   			 }
	   			 
	   			 distances[DistancesCount] = Math.sqrt(x_minus*x_minus + y_minus*y_minus);
	   			 distances2[DistancesCount] = distances[DistancesCount] + suffix[j];       // good 120ms
	   			 //System.out.println(distances[DistancesCount]+" j: "+j);
	   			 DistancesCount++;
	   			 
	   			 if(DistancesCount == TwoPercentIndex){          // j+1 may be  // DistancesCount
	   				Arrays.sort(distances, 0, DistancesCount);
	   				greatest = distances[5*NumRes[i]-1];
	   				//System.out.println("greatest: "+greatest);
	   				CheckFlag = true;
	   			 }else if(DistancesCount == TenPercentIndex){       // j+1 may be  // DistancesCount
		   				Arrays.sort(distances, 0, DistancesCount);
		   				greatest = distances[5*NumRes[i]-1];
		   				//System.out.println("greatest: "+greatest);
		   		 }
	   			  
	          }
             // System.out.print("2%: "+TwoPercentIndex+"\n"+"10%: "+TenPercentIndex+"\n");  
	      } // else ends here
   			//System.out.println(DistancesCount+"  i:"+i);
   		    Arrays.sort(distances, 0, DistancesCount);      // Max: 300 ms for Current Case...Pending: Try for Random case
	        Arrays.sort(distances2, 0, DistancesCount);
	     
	    if(QueryRes[i] == 't'){            // Topic Processing
	              		
   			if(NumRes[i] > DistancesCount){           
       			NumberOfResults = DistancesCount;         
   			}else if(DistancesCount-NumRes[i] >= 10) {
       			NumberOfResults = NumRes[i]+10; 
       		}else if(DistancesCount-NumRes[i] >= 1) {
       			NumberOfResults = DistancesCount;
       		}else {
       			NumberOfResults = NumRes[i];
       		}
   			
       		ResultTopicIds[i] = new int[NumberOfResults];
       		
       		// Filling ResultTopicIds array
       		for(int n=0; n < NumberOfResults; n++){       
       		    IndexOfTopicIdd = (int)((distances2[n]-distances[n]-1)*100000000.0) ;
       		    ResultTopicIds[i][n] = AllTopics[IndexOfTopicIdd];         // Good only for Topics...Not for Questions
       		}
       		
       		int startIndex = 0, toIndex; 
       	    // Sorting Topic Ids stored in 'ResultTopicIds[i]' array acc. to threshold: 0.001
       		for(int m=1; m < NumberOfResults; m++){ 
   		       if(NumberOfResults-m != 1 && Math.abs(distances[m-1] - distances[m]) <= 0.001){
   		    	   continue;
   		       }else{
   		    	   toIndex = m;
   		    	   if(NumberOfResults - m == 1){ 
   		    		   if(Math.abs(distances[m-1] - distances[m]) > 0.001)
   		    			   toIndex = m;
   		    		   else 
   		    			   toIndex = m + 1 ;
   		    	   }
   		    	   
   		    	   /* Sorting all Topic ID's which are Equi-distant from each other based on Threshold: 0.001
   		    	    * These Equi-distant Topic Id's are somewhere in ResultTopicIds[i] array and
   		    	    * they start from index 'startIndex' and end at index 'toIndex'...
   		    	    * We are sorting these Topic Ids...because they are Equi-distant
   		    	    */
   		    	   if(toIndex-startIndex > 1)
   		    	   {   Arrays.sort(ResultTopicIds[i], startIndex, toIndex);
       		    	   /* Since we need Equi-distant Topic Id's in Descending order
       		    	    * here i am reversing Sorted section. 
       		    	    */
       		    	   int right= m-1;
       		    	   if(toIndex == m + 1)     // for last i...
       		    		   right = m;
       		    	   for (int left=startIndex; left<right; left++, right--) {    // for reversing the elements that we just sorted
       		    		    int temp = ResultTopicIds[i][left]; 
       		    		    ResultTopicIds[i][left]  = ResultTopicIds[i][right]; 
       		    		    ResultTopicIds[i][right] = temp;
       		    	   }
   		    	   }  // if
   		    	   startIndex = m;       // Setting 'startIndex' for next Equidistant Section
       		    } // else
   		    } // for 
   		}else {     // Question Processing 'q'
   			   		
   			int NumOfQuestToReturn;               // NumOfQuestToReturn = Actual no. of Questions to Return 
   			if(NumRes[i] > ActualNumOfReturnableQuestions)       // Total Number of Questions present
       		    NumOfQuestToReturn = ActualNumOfReturnableQuestions;
       		else
       		    NumOfQuestToReturn = NumRes[i];
   			
   			int countUnique =0, TopicId, 
   				n=0, startIndex = 0, toIndex,
   				PrintCount = 0; 
			
   			int[] printed = new int[NumOfQuestToReturn];
   			
   			if(DistancesCount == 1){
   			    IndexOfTopicIdd = (int)((distances2[0]-distances[0]-1)*100000000.0) ;
                TopicId = AllTopics[IndexOfTopicIdd]; 
                int[] ToPrintWithDuplicates = mapTopicToQues.get(TopicId);
                Arrays.sort(ToPrintWithDuplicates);
                
                for(int f=ToPrintWithDuplicates.length-1; f>=0; f--) {
					if(!NumExistsInArray(printed , ToPrintWithDuplicates[f] , PrintCount) && PrintCount < NumOfQuestToReturn) {
					    printed[PrintCount++] = ToPrintWithDuplicates[f];
					    countUnique = PrintCount;
					}
				} // for
                if(T == 1) {        
                   ResultTopicIds[i] = printed;
                }else {
                   ResultTopicIds[i] = CallMainQueryProcessing(i, distances, distances2);
                }
   			}else{
   				 int lastCase = 1;
       			 //while(countUnique < NumOfQuestToReturn  && n <= T-2 ){  // No entry when T=1...so consider it
       		     while(countUnique < NumOfQuestToReturn  && n <= DistancesCount-2 ){    
       				if(n != DistancesCount-2 && Math.abs(distances[n] - distances[n+1]) <= 0.001) {
           			    	
           			}else{
           				toIndex = n+1 ;
           	            
           				if(n >= 5*NumRes[i]-2){          
           					flag_2 = true;
           					if(flag_1){
           						break;
           					}
           				}
           					
           				if(n == DistancesCount-2){
           				    if(Math.abs(distances[n] - distances[n+1]) > 0.001){
        		    			   toIndex = n+1;
        		    			   lastCase = 2;       // last distance in distances array
        		    		}else {
        		    			   toIndex = n+2 ;
        		    		}	
           				}
           				
           		       for(int e=0; e < lastCase; e++){	  	
	            				
           		    	    if(e == 1){         //last distance
           		    	    	startIndex = n+1;
           		    	    	toIndex = n+2;
           		    	    }
           		    	   
           		    	    int countEquiQues =0 ;
	            				
            				int[] TopicIds = new int[toIndex-startIndex];
            				int countTopicIds=0;
            				for(int f=startIndex; f < toIndex; f++) {
            					IndexOfTopicIdd = (int)((distances2[f]-distances[f]-1)*100000000.0) ;
            					TopicIds[countTopicIds++] = AllTopics[IndexOfTopicIdd];         // Good only for Topics...Not for Questions			 	   						
            				}  // f
        					for(int f=0; f < countTopicIds; f++) {
        						countEquiQues += mapTopicToNumQues.get(TopicIds[f]); 	   						
            				}  // f
        					int[] EquiTopicQuesIds = new int[countEquiQues];
        					int qcount=0;
         					for(int f=0; f < countTopicIds; f++) {
           				        for(int d=0; d < mapTopicToNumQues.get(TopicIds[f]); d++){
           				        	EquiTopicQuesIds[qcount++] = mapTopicToQues.get(TopicIds[f])[d];
           				        }
            				}  // f
         					Arrays.sort(EquiTopicQuesIds);
         					
         					for(int f=EquiTopicQuesIds.length-1; f>=0; f--) {
         						if(!NumExistsInArray(printed , EquiTopicQuesIds[f] , PrintCount) && PrintCount < NumOfQuestToReturn) {
         						    printed[PrintCount++] = EquiTopicQuesIds[f];
         						    countUnique = PrintCount;
         						}
         					}
        					// here we are supposed to have an array of questions...with equiDistant topics	
         					startIndex = n+1 ;	
           		      } // for e
           		         
           		    }  // else
           		    n++ ;
       			 }   // while
       		    
   			     if(flag_1 && flag_2){  
   			    	ResultTopicIds[i] = CallMainQueryProcessing(i, distances, distances2);
 			     }else {
 			    	ResultTopicIds[i] = printed;
 			     }
   		    } // Inner else	 T>=1
   		   	
   		}   // else 'q'
   		
   	  }   // Query Processing Ends here
   	
   }  // method ends here
   
   public static int[] CallMainQueryProcessing(int i, double[] distances, double[] distances2 ){
	   
	   double x_minus, y_minus;
	   int IndexOfTopicIdd, TopicId;
       
	   for(int j=0; j < T; j++){                // going through all topics in Single Query
	    	x_minus = TopCoord[j][0]- QCoord[i][0];
   			y_minus = TopCoord[j][1]- QCoord[i][1];
   			distances[j] = Math.sqrt(x_minus*x_minus + y_minus*y_minus); 
   			distances2[j] = distances[j] + suffix[j];       // good 120 ms
        }
       	
	    Arrays.sort(distances);      // Max: 300 ms for Current Case...
        Arrays.sort(distances2);
    		
		int NumOfQuestToReturn;               // NumOfQuestToReturn = Actual no. of Questions to Return 
		if(NumRes[i] > ActualNumOfReturnableQuestions)       // Total Number of Questions present
	         NumOfQuestToReturn = ActualNumOfReturnableQuestions;
	    else
	         NumOfQuestToReturn = NumRes[i];

		int countUnique =0, n=0, startIndex = 0, toIndex,
			 PrintCount = 0; 
	
		int[] printed = new int[NumOfQuestToReturn];
		
		if(T == 1){
			IndexOfTopicIdd = (int)((distances2[0]-distances[0]-1)*100000000.0) ;
            TopicId = AllTopics[IndexOfTopicIdd]; 
            int[] ToPrintWithDuplicates = mapTopicToQues.get(TopicId);
            Arrays.sort(ToPrintWithDuplicates);
            
            for(int f=ToPrintWithDuplicates.length-1; f>=0; f--) {
				if(!NumExistsInArray(printed , ToPrintWithDuplicates[f] , PrintCount) && PrintCount < NumOfQuestToReturn) {
				    printed[PrintCount++] = ToPrintWithDuplicates[f];
				    countUnique = PrintCount;
				    //out.write(EquiTopicQuesIds[f]+" ");
				}
			} // for
               
		}else{
		  int lastCase = 1;
		  //while(countUnique < NumOfQuestToReturn  && n <= T-2 ){  // No entry when T=1...so consider it
	      while(countUnique < NumOfQuestToReturn  && n <= T-2 ){    
			if(n != T-2 && Math.abs(distances[n] - distances[n+1]) <= 0.001) {
  			    	
  			}else{
  				toIndex = n+1 ;
  				if(n == T-2){
  				   if(Math.abs(distances[n] - distances[n+1]) > 0.001){
	    			   toIndex = n+1;
	    			   lastCase = 2;       // last distance in distances array
	    		   }else {
	    			   toIndex = n+2 ;
	    		   }	
  				}
  				
  		       for(int e=0; e < lastCase; e++){	  	
       				
  		    	    if(e == 1){         //last distance
  		    	    	startIndex = n+1;
  		    	    	toIndex = n+2;
  		    	    }
  		    	   
  		    	    int countEquiQues =0 ;
       				
	   				int[] TopicIds = new int[toIndex-startIndex];
	   				int countTopicIds=0;
	   				for(int f=startIndex; f < toIndex; f++) {
	   					IndexOfTopicIdd = (int)((distances2[f]-distances[f]-1)*100000000.0) ;
	   					TopicIds[countTopicIds++] = AllTopics[IndexOfTopicIdd];         // Good only for Topics...Not for Questions			 	   						
	   				}  // f
					for(int f=0; f < countTopicIds; f++) {
						countEquiQues += mapTopicToNumQues.get(TopicIds[f]); 	   						
	   				}  // f
					int[] EquiTopicQuesIds = new int[countEquiQues];
					int qcount=0;
						for(int f=0; f < countTopicIds; f++) {
	  				        for(int d=0; d < mapTopicToNumQues.get(TopicIds[f]); d++){
	  				        	EquiTopicQuesIds[qcount++] = mapTopicToQues.get(TopicIds[f])[d];
	  				        }
	   				}  // f
					Arrays.sort(EquiTopicQuesIds);
					
					for(int f=EquiTopicQuesIds.length-1; f>=0; f--) {
						if(!NumExistsInArray(printed , EquiTopicQuesIds[f] , PrintCount) && PrintCount < NumOfQuestToReturn) {
						    printed[PrintCount++] = EquiTopicQuesIds[f];
						    countUnique = PrintCount;
						    //out.write(EquiTopicQuesIds[f]+" ");
						}
					}
				// here we are supposed to have an array of questions...with equiDistant topics	             
					startIndex = n+1 ;	
  		      } // for e
  		       
  		    }  // else
  		    n++ ;
		  }   // while
        } // else  
		return printed;
   }   // method ends here
   
   /* PrintOutPut()
    * Displays Output for all Queries
    */
   public static void PrintOutput() {
	    int NumberOfResults;        // NumberOfResults = Actual no. of Topics to Return
	   	
	    for(int i=0; i < N; i++){			 
	   		
	   		if(QueryRes[i] == 't'){
	   			if(NumRes[i] > T)
	 	   		   NumberOfResults = T;
	 	   		else
	 	   		   NumberOfResults = NumRes[i];
	   			
	    		for(int j=0; j < NumberOfResults; j++) { 
					try{ 
						out.write(ResultTopicIds[i][j] + " ");
					}catch (IOException e) {
						e.printStackTrace();
					}
	    		}	
	   		}
	   		else {	       // If query return type is 'q'
	   			int NumOfQuestToReturn;                            // NumOfQuestToReturn = Actual no. of Questions to Return 
	   			if(NumRes[i] > ActualNumOfReturnableQuestions)     // Total Number of Questions present
	       			NumOfQuestToReturn = ActualNumOfReturnableQuestions;
	       		else
	       			NumOfQuestToReturn = NumRes[i]; 
	   			
	   			for(int j=0; j < NumOfQuestToReturn; j++)
					try {
						out.write(ResultTopicIds[i][j] + " ");
					} catch (IOException e) {
		                e.printStackTrace();
					}		   	    				
	   		}   // else 'q' ends here...
	   		
	   	   try {
			   out.newLine();
		   } catch (IOException e){ 
			   e.printStackTrace();
		   }   
	   	} // for loop     	   
   } // Method PrintOutput Ends here...

}  //  Solution Class ends here
