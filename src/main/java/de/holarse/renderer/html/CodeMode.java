package de.holarse.renderer.html;

import de.holarse.renderer.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodeMode implements Mode {

    Logger logger = LoggerFactory.getLogger(CodeMode.class);
    
    private final StringBuilder buffer = new StringBuilder(30);    

    @Override
    public void handle(char ch) {
        buffer.append(ch);
    }

    @Override
    public StringBuilder render() {
        final StringBuilder n = new StringBuilder(250);
        n.append( buffer.toString().replaceAll("\\[code\\]", "<pre><code class=\"language-bash\">").replaceAll("\\[/code\\]", "</code></pre>") );
        return n;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isComplete() {
        return buffer.toString().toUpperCase().endsWith("[/CODE]");
    }
    
}
