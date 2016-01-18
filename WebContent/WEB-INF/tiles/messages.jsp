<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div id="messages">


</div>

<script type="text/javascript">

	

	var timer;
	function showReply(id){
		var display = $("#form"+id).css('display')==='none';
		if ( display === true ) {
			  $("#form"+id).show();
			  stopTimer();
			} else if ( display === false ) {
			  $("#form"+id).hide();
			  startTimer();
			}
	}

	function success(data){
		var formId = data.id;
		$("#form"+formId).hide();
		$("#alert"+formId).text("Message Sent!")
		startTimer();
	}

	function error (data){
		alert("error");
	}

	function sendMessage(id, name, email){
		var text = $("#textarea"+id).val();

		$('#replyButton').prop("Value", "Sending....");
		$.ajax({
				"type":'POST',
				"url":"<c:url value='/sendmessage'/>",
				"data": JSON.stringify({
									  "id":id,
									  "text":text,
									  "name":name,
									  "email":email}),
				"success":success,
				"error":error,
				"contentType":"application/json",
				"dataType":"json"
		})
			
		alert(textarea + ": " + name +": " + email);
	}

	function showMessages(data) {
		$("div#messages").html("");
		var messages = data.messages;
		jQuery.each(messages, function(index, message){
			
			var messageDiv = document.createElement("div");
			messageDiv.setAttribute("class", "message");
			
			var subjectSpan = document.createElement("span");
			subjectSpan.setAttribute("class", "subject");
			subjectSpan.appendChild(document.createTextNode(message.subject));

			messageDiv.appendChild(subjectSpan);

			var contentSpan = document.createElement("span");
			contentSpan.setAttribute("class", "messageBody");
			contentSpan.appendChild(document.createTextNode(message.content));

			messageDiv.appendChild(contentSpan);

			var nameSpan = document.createElement("span");
			nameSpan.setAttribute("class", "name");
			nameSpan.appendChild(document.createTextNode(message.name + " ("));

			var link = document.createElement("a");
			link.setAttribute("class", "replylink");
			link.setAttribute("href", "#");
			link.setAttribute("onclick", "showReply("+index+")");
			link.appendChild(document.createTextNode(message.email));

			nameSpan.appendChild(link);
			
			nameSpan.appendChild(document.createTextNode(")"));
			
			messageDiv.appendChild(nameSpan);


			var alertSpan = document.createElement("span");
			alertSpan.setAttribute("class", "alert");
			alertSpan.setAttribute("id", "alert"+index);
			//alertSpan.appendChild(document.createTextNode("Sent!"));
			
			
			messageDiv.appendChild(alertSpan);

			var replyForm = document.createElement("form");
			replyForm.setAttribute("id", "form"+index);
			replyForm.setAttribute("class", "replyform");

			var textArea = document.createElement("textarea");
			textArea.setAttribute("class", "replyarea");
			textArea.setAttribute("id", "textarea"+index);

			var replyButton = document.createElement("input");
			replyButton.setAttribute("class", "replybutton");
			replyButton.setAttribute("id", "replyButton");
			replyButton.setAttribute("type", "button");
			replyButton.setAttribute("value", "Reply");
			replyButton.onclick = function(j, name, email){
				return function(){
					sendMessage(j, name, email);
				}
			}(index, message.name, message.email);
			
			replyForm.appendChild(textArea);
			replyForm.appendChild(replyButton)

			messageDiv.appendChild(replyForm);
			
			$("div#messages").append(messageDiv);
		})
	}

	function onLoad() {
		updatePage();
		startTimer();
	}

	function startTimer(){
		timer = window.setInterval(updatePage, 5000);
	}
	
	function stopTimer(){
		window.clearInterval(timer);
	}

	function updatePage(){
		$.getJSON("<c:url value='/getmessages'/>").done(showMessages)
		.fail(function(json) {
			console.log(json);
		});
	}

	$(document).ready(onLoad);
</script>