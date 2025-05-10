void permuteString(String in){
	//input validation
	if(in == null || in.length() == 0) throw new IllegalArgumentException();
	
	permuteStep("",in);
}

void permuteStep(String head, String tail){
	if(tail.length < 1) println(head);
	else for(int i=0;i<tail.length();i++){
		permuteStep(head + tail[i], stringWithout(i));
	}
}

String stringWithout(String str, int index){
	return (str.substring(0,index)+str.substring(index+1));
}