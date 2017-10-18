/**
 * 
 */

function flipCard(X){
		//console.log("flipCard");
		vocab = $("#word");
		defination = $("#defination");
		if("defination"==X)
		{
				defination.addClass("hideCard");
				vocab.removeClass("hideCard");
				vocab.addClass("animated flipInX");
				defination.removeClass("animated flipInX");
		}
		if("vocab"==X)
		{
			vocab.addClass("hideCard");
			defination.removeClass("hideCard");
			defination.addClass("animated flipInX");
			vocab.removeClass("animated flipInX");
		}
}

function requestNextWord(){
	$('#nxtCard').attr('disabled','disabled');
	$('#nxtCard').css("cursor", "default");
	
	$.ajax({
		  method:"GET",
		  url: "CardsController",
		  dataType:"json"
		  //context: document.body
	}).done(function(data) {
		console.log(data.status);
		if(data.status==false){
			$("#word").html("<h1>You have Exhausted all Words</h1>");
			$("#defination").html("<h1>You have Exhausted all Words</h1>");
		}
		else{
			$("#pageNo").html("Page No. "+data.pageNo);
			$("#cid").html(data.cid);
			$("#name").html(data.word);
			$("#type").html(data.type);
			$("#other").html(data.other);
			$("#def").html(data.def);
			$("#usage").html(data.usage);
			$("#relWords").html(data.relWords);
			$("#info").html(data.info);
			$("#status").html(data.status);
		}
	$('#nxtCard').removeAttr('disabled');
		
	});
}

$("document").ready(function(){
	
	vocab = $("#word");
	defination = $("#defination");
	requestNextWord();
	
	$("#flipBtn").click(function(){
		
		if(vocab.hasClass("hideCard"))
		{
			flipCard("defination");
		}
		else{
			
			flipCard("vocab");
		}
			
	});
	
	$("#nxtCard").click(function(){
		console.log("NextCard");
		
		$("#flashCards").removeClass("rollIn");
		$("#flashCards").addClass("rollOut");
		
		requestNextWord();
		
		setTimeout(function(){
			$("#flashCards").removeClass("rollOut");
			$("#flashCards").addClass("rollIn");
			if(vocab.hasClass("hideCard"))
				flipCard("defination");
		}, 1000);
	});
	
	
	
});