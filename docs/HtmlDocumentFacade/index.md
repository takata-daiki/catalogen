# HtmlDocumentFacade

***

## [Cluster 1](./1)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
168. private final HtmlDocumentFacade htmlDocumentFacade;
193.         htmlDocumentFacade.getBody().appendChild( notes );
195.     htmlDocumentFacade.updateStylesheet();
200.     return htmlDocumentFacade.getDocument();
207.     Element span = htmlDocumentFacade.document.createElement( "span" );
235.         htmlDocumentFacade.addStyleClass( span, "s", style.toString() );
237.     Text textNode = htmlDocumentFacade.createText( text );
249.         Element bookmarkElement = htmlDocumentFacade
264.         htmlDocumentFacade.setTitle( summaryInformation.getTitle() );
267.         htmlDocumentFacade.addAuthor( summaryInformation.getAuthor() );
270.         htmlDocumentFacade.addKeywords( summaryInformation.getKeywords() );
273.         htmlDocumentFacade
288.     Element select = htmlDocumentFacade.createSelect();
291.         select.appendChild( htmlDocumentFacade.createOption( values[i],
302.     Element img = htmlDocumentFacade.createImage( path );
327.     Element basicLink = htmlDocumentFacade.createHyperlink( hyperlink );
391.         root = htmlDocumentFacade.createBlock();
392.         htmlDocumentFacade.addStyleClass( root, "d",
397.         Element inner = htmlDocumentFacade.createBlock();
398.         htmlDocumentFacade.addStyleClass( inner, "d",
403.         Element image = htmlDocumentFacade.createImage( imageSourcePath );
404.         htmlDocumentFacade.addStyleClass( image, "i",
414.         root = htmlDocumentFacade.createImage( imageSourcePath );
427.     currentBlock.appendChild( htmlDocumentFacade.document
435.     block.appendChild( htmlDocumentFacade.createLineBreak() );
442.     final String textIndexClass = htmlDocumentFacade.getOrCreateCssClass(
447.     Element anchor = htmlDocumentFacade.createHyperlink( "#"
457.         notes = htmlDocumentFacade.createBlock();
461.     Element note = htmlDocumentFacade.createBlock();
465.     Element bookmark = htmlDocumentFacade.createBookmark( forwardNoteLink );
471.     note.appendChild( htmlDocumentFacade.createText( " " ) );
473.     Element span = htmlDocumentFacade.getDocument().createElement( "span" );
491.     flow.appendChild( htmlDocumentFacade.createLineBreak() );
498.     Element basicLink = htmlDocumentFacade.createHyperlink( "#" + pageref );
510.     final Element pElement = htmlDocumentFacade.createParagraph();
562.                 Element span = htmlDocumentFacade.getDocument()
564.                 htmlDocumentFacade
571.                 Text textNode = htmlDocumentFacade.createText( bulletText
594.         htmlDocumentFacade.addStyleClass( pElement, "p", style.toString() );
603.     Element div = htmlDocumentFacade.createBlock();
604.     htmlDocumentFacade.addStyleClass( div, "d", getSectionStyle( section ) );
605.     htmlDocumentFacade.body.appendChild( div );
614.     htmlDocumentFacade.addStyleClass( htmlDocumentFacade.body, "b",
617.     processParagraphes( wordDocument, htmlDocumentFacade.body, section,
624.     Element tableHeader = htmlDocumentFacade.createTableHeader();
625.     Element tableBody = htmlDocumentFacade.createTableBody();
641.         Element tableRowElement = htmlDocumentFacade.createTableRow();
663.                 tableCellElement = htmlDocumentFacade
668.                 tableCellElement = htmlDocumentFacade.createTableCell();
697.                 tableCellElement.appendChild( htmlDocumentFacade
701.                 htmlDocumentFacade.addStyleClass( tableCellElement,
709.             tableRowElement.setAttribute( "class", htmlDocumentFacade
722.     final Element tableElement = htmlDocumentFacade.createTable();
726.                     htmlDocumentFacade
{% endhighlight %}

***

