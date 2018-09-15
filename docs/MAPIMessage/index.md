# MAPIMessage

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
40. private final MAPIMessage msg;
55.        msg.setReturnNullOnMissingChunk(true);
57.        String subject = msg.getSubject();
58.        String from = msg.getDisplayFrom();
62.        metadata.set(Metadata.MESSAGE_TO, msg.getDisplayTo());
63.        metadata.set(Metadata.MESSAGE_CC, msg.getDisplayCC());
64.        metadata.set(Metadata.MESSAGE_BCC, msg.getDisplayBCC());
67.        metadata.set(Metadata.SUBJECT, msg.getConversationTopic());
70.        for(String recipientAddress : msg.getRecipientEmailAddressList()) {
78.        if(msg.getMessageDate() != null) {
79.           metadata.set(Metadata.EDIT_TIME, msg.getMessageDate().getTime().toString());
80.           metadata.set(Metadata.LAST_SAVED, msg.getMessageDate().getTime().toString());
84.              String[] headers = msg.getHeaders();
107.        header(xhtml, "To", msg.getDisplayTo());
108.        header(xhtml, "Cc", msg.getDisplayCC());
109.        header(xhtml, "Bcc", msg.getDisplayBCC());
111.            header(xhtml, "Recipients", msg.getRecipientEmailAddress());
115.        xhtml.element("p", msg.getTextBody());
117.        for (AttachmentChunks attachment : msg.getAttachmentFiles()) {
{% endhighlight %}

***

