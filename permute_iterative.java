void permuteString(String in){
	//input validation
	if(in == null || in.length() == 0) throw new IllegalArgumentException();
	
	String sortedIn = bubbleSort(in);
	println(sortedIn);
	char[] charArray = sortedIn.toCharArray();	
	int n = charArray.length;
	
	//permute via Heap's algorithm
	int[] swapArray = zeroArray(n);
	int i = 1;
	while(i<n){
		if(c[i]<i){
			if(i%2==0) charArraySwap(charArray,0,i);
			else charArraySwap(charArray,swapArray[i],i);
			println(new String(charArray));
			swapArray[i]++;
			i==1;
		} else {
			swapArray[i] = 0;
			i++;
		}
	}	
	
}

String bubbleSort(String in){
	//input validation
	if(in.length() < 2) return in;
	
	char[] sortArray = in.toCharArray();
	for(int i = 1; i < sortArray.length; i++){
		for(int j = i; j < sortArray.length; j++){
			if(sortArray[j] < sortArray[j-1]) charArraySwap(sortArray,j,j-1);
		}
	}

	String out = new String(sortArray);
	return out;
}

void charArraySwap(char[] arr, int a, int b){
	//input validation
	if(a >= arr.length || b >= arr.length) throw new IllegalArgumentException();

	char swap = arr[a];
	arr[a] = arr[b];
	arr[b] = swap;
}

int[] zeroArray(int length){
	int[] out = new int[length];
	for(int i = 0; i<n; i++){
		out = 0;
	}
	return out;
}