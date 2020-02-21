<H1>Todo Application</H1>

  A sample android app that shows Clean Architecture implementation using MVP design pattern. 
  
<b>Implemented by Clean Architecture</b>

<ul>
<li>Presentation (Activity/Fragment and Presenter)</li>
<li>Domain (Interactor/Usecases, Repository Interface and Model classes)</li>
<li>Data (Local/remote datastore and Repository implementation)</li>
</ul>

<b>Dependencies between components</b>
 
 Activity/Fragment  --> Presenter --> Usecase(Interactor) --> Repository --> LocalDataStore(Room DB)

<b>Communication between layers</b>

All the layers are communicated through callbacks.

<ol>
  <li>UI calls method from ViewModel.</li>
  <li>ViewModel executes Use case.</li>
  <li>Use case combines data from Album and Photo Repositories.</li>
  <li>Each Repository returns data from a Data Source (Cached or Remote).</li>
  <li>Information flows back to the UI where we display the list of posts.</li>
</ol>
<b>Other important components</b>
<ul>
  <li>Navigator</li>
  <li>ResourceManger</li>
 </ul>
 
<b>Scenario</b>

At a glance:
<ul>
  <li>Use can either Register or Login if already registered</li>
  <li>Once authenticated, show list of todos with option to add new todo</li>
  <li>Once tap on each item, show edit/delete todo screen</li>
 </ul>

<b>References</b>
