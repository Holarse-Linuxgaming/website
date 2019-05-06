<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="article-editor" data-nodeid="${nodeId}">
    <form>
        <label for="mainTitle">Titel</label>
        <input id="mainTitle" v-model="node.mainTitle" placeholder="Titel des Artikels">
        
        <textarea id="content" v-model="node.content"></textarea>
        
        <button class="btn btn-primary" v-on:click="submit">Speichern</button>
    </form>    
</div>

