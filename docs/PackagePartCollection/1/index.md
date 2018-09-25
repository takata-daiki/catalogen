# PackagePartCollection @Cluster 1 (activepart, getpartname, string)

***

### [OPCPackage.java](https://searchcode.com/codesearch/view/97406292/)
> test that we get the same value as excel and , for 
{% highlight java %}
84. protected PackagePartCollection partList;
533.   for (PackagePart part : partList.values()) {
565.     for (PackagePart part : partList.values()) {
626.       if (partList.containsKey(part._partName))
654.           partList.put(unmarshallPart._partName, unmarshallPart);
673.           partList.put(part._partName, part);
680.   return new ArrayList<PackagePart>(partList.values());
733.   if (partList.containsKey(partName)
734.       && !partList.get(partName).isDeleted()) {
761.   this.partList.put(partName, part);
826.   if (partList.containsKey(part._partName)) {
827.     if (!partList.get(part._partName).isDeleted()) {
837.     this.partList.remove(part._partName);
839.   this.partList.put(part._partName, part);
871.   if (this.partList.containsKey(partName)) {
872.     this.partList.get(partName).setDeleted(true);
874.     this.partList.remove(partName);
924.   PackagePart relPart = this.partList.get(PackagingURIHelper
927.   PackagePart partToRemove = this.partList.get(partName);
{% endhighlight %}

***

