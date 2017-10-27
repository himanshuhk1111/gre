/**
 * 
 */

var card = null;
var status = null;

function buttonDisable(){
	$('#nxtCard').attr('disabled','disabled');
	$('#nxtCard').css("cursor", "default");
	$('#prevCard').attr('disabled','disabled');
	$('#prevCard').css("cursor", "default");
}

function buttonEnable(){
	$('#nxtCard').removeAttr('disabled');
	$('#prevCard').removeAttr('disabled');
}

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

function requestWord(cid){
	$.ajax({
		  method:"GET",
		  //async:false,
		  url: "CardsController",
		  dataType:"json",
		  data:{
			  cid:cid
		  }
		  //context: document.body
	}).done(function(data) {
		console.log(data.status);
		console.log("here "+ card);
		if(data.status==false){
			status = data.status;
			card = card + 1 ;
			$("#name").html("You have Exhausted all Words or bad request");
			$("#def").html("You have Exhausted all Words or bad request");
			$("#pageNo").html("");
			$("#cid").html("");
			$("#type").html("");
			$("#other").html("");
			$("#usage").html("");
			$("#relWords").html("");
			$("#info").html("");
			$("#status").html("");
		}
		else{
			card = parseInt(data.cid);
			status = data.status;
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
		buttonEnable();
	});
}

$("document").ready(function(){
	
	x = location.pathname;
	if(x.indexOf("/cards.html")!=-1){
		vocab = $("#word");
		defination = $("#defination");
		requestWord(1);
		
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
			buttonDisable();
			
			$("#flashCards").removeClass("rollInRight");
			$("#flashCards").removeClass("rollInLeft");	
			$("#flashCards").addClass("rollOutLeft");
			
			console.log(status);
			if(status!="false")
				requestWord(card+1);
			
			setTimeout(function(){
				$("#flashCards").removeClass("rollOutLeft");
				$("#flashCards").removeClass("rollOutRight");
				$("#flashCards").addClass("rollInRight");
				if(vocab.hasClass("hideCard"))
					flipCard("defination");
			}, 1000);
			
		});

		$("#prevCard").click(function(){
			console.log("prevCard");
			
			$("#flashCards").removeClass("rollInRight");
			$("#flashCards").removeClass("rollInLeft");	
			$("#flashCards").addClass("rollOutRight");
			
			console.log(status);
			
			if(card>1)
			{
				buttonDisable();
				requestWord(card-1);
			}
				
			setTimeout(function(){
				$("#flashCards").removeClass("rollOutLeft");
				$("#flashCards").removeClass("rollOutRight");
				$("#flashCards").addClass("rollInLeft");
				if(vocab.hasClass("hideCard"))
					flipCard("defination");
			}, 1000);
		});
	}
	
});