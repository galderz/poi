package org.apache.poi.hssf.usermodel;

import junit.framework.TestCase;

import java.awt.*;
import java.io.FileOutputStream;

/**
 * Tests the Graphics2d drawing capability.
 *
 * @author Glen Stampoultzis (glens at apache.org)
 */
public class TestEscherGraphics2d extends TestCase
{
    private HSSFShapeGroup escherGroup;
    private EscherGraphics2d graphics;

    protected void setUp() throws Exception
    {
        super.setUp();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("test");
        escherGroup = sheet.createDrawingPatriarch().createGroup(new HSSFClientAnchor(0,0,1023,255,(short)0,0,(short) 0,0));
        escherGroup = new HSSFShapeGroup(null, new HSSFChildAnchor());
        EscherGraphics g = new EscherGraphics(this.escherGroup, workbook, Color.black, 1.0f);
        graphics = new EscherGraphics2d(g);

    }

    public void testDrawString() throws Exception
    {
        graphics.drawString("This is a test", 10, 10);
        HSSFTextbox t = (HSSFTextbox) escherGroup.getChildren().get(0);
        assertEquals("This is a test", t.getString().toString());
    }

    public void testFillRect() throws Exception
    {
        graphics.fillRect( 10, 10, 20, 20 );
        HSSFSimpleShape s = (HSSFSimpleShape) escherGroup.getChildren().get(0);
        assertEquals(HSSFSimpleShape.OBJECT_TYPE_RECTANGLE, s.getShapeType());
        assertEquals(10, s.getAnchor().getDx1());
        assertEquals(10, s.getAnchor().getDy1());
        assertEquals(30, s.getAnchor().getDy2());
        assertEquals(30, s.getAnchor().getDx2());
    }

    public void testGetFontMetrics() throws Exception
    {
        FontMetrics fontMetrics = graphics.getFontMetrics(graphics.getFont());
        assertEquals(7, fontMetrics.charWidth('X'));
        assertEquals("java.awt.Font[family=Arial,name=Arial,style=plain,size=10]", fontMetrics.getFont().toString());
    }

    public void testSetFont() throws Exception
    {
        Font f = new Font("Helvetica", 0, 12);
        graphics.setFont(f);
        assertEquals(f, graphics.getFont());
    }

    public void testSetColor() throws Exception
    {
        graphics.setColor(Color.red);
        assertEquals(Color.red, graphics.getColor());
    }

    public void testGetFont() throws Exception
    {
        Font f = graphics.getFont();
        assertEquals("java.awt.Font[family=Arial,name=Arial,style=plain,size=10]", f.toString());
    }
}
