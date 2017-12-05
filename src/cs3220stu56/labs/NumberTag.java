package cs3220stu56.labs;
        

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class NumberTag extends SimpleTagSupport {
	
	int op1;
	
    public NumberTag(){
    	op1 = 0;
    }

    public void setOp1( int i )
    {
        op1 = i;
    }


    @Override
    public void doTag() throws JspException, IOException
    {
    	 JspWriter out = getJspContext().getOut();
    	 NumberToWordsConverter str = new NumberToWordsConverter(op1);
         out.print( str.getStringValue());
    }

}
