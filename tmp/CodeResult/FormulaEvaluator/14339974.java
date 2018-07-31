/*
 * Created by JFormDesigner on Tue Jul 31 17:05:30 EDT 2007
 */

package repast.simphony.data.gui.formula;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.pietschy.wizard.InvalidStateException;
import org.pietschy.wizard.PanelWizardStep;
import org.pietschy.wizard.WizardModel;

import repast.simphony.data.array.IllegalFormulaException;
import repast.simphony.data.gui.DataMappingWizardModel;
import repast.simphony.data.logging.formula.FormulaEvaluator;
import repast.simphony.data.logging.formula.ParseException;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.Sizes;

/**
 * @author User #2
 */
public class FormulaScriptStep extends PanelWizardStep {

  private DataMappingWizardModel model;

  public FormulaScriptStep() {
    super("Formula Script Mapping", "Please fill in the items below to create a Formula Script mapping");
    initComponents();

    scriptArea.setDocument(new PlainDocument() {
      @Override
      public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        super.insertString(offs, str, a);
        setComplete(this.getLength() > 0);
      }

      @Override
      public void remove(int offs, int len) throws BadLocationException {
        super.remove(offs, len);
        setComplete(this.getLength() > 0);
      }
    });
  }


  @Override
  public void init(WizardModel wizardModel) {
    super.init(wizardModel);
    this.model = (DataMappingWizardModel) wizardModel;
  }

  @Override
  public void prepare() {
    super.prepare();
  }

  @Override
  public void applyState() throws InvalidStateException {
    super.applyState();
    String script = scriptArea.getText();
    List<Class> classes = new ArrayList<Class>();
    classes.add(model.getAgentClass());
    try {
      FormulaEvaluator eval = new FormulaEvaluator(script, classes);
      model.setMappingRepresentation(new FormulaScriptRepresentation(eval, aggBox.isSelected()));
    } catch (ParseException e) {
      throw new InvalidStateException("Invalid formula syntax", e);
    } catch (IllegalFormulaException ex)  {
      throw new InvalidStateException(ex.getMessage(), ex);
    }

  }

  private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner non-commercial license
		DefaultComponentFactory compFactory = DefaultComponentFactory.getInstance();
		separator1 = compFactory.createSeparator("Aggregate");
		aggBox = new JCheckBox();
		separator2 = compFactory.createSeparator("Script");
		scrollPane1 = new JScrollPane();
		scriptArea = new JTextArea();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new FormLayout(
			ColumnSpec.decodeSpecs("default:grow"),
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				new RowSpec(RowSpec.FILL, Sizes.DEFAULT, FormSpec.DEFAULT_GROW)
			}));
		add(separator1, cc.xy(1, 1));

		//---- aggBox ----
		aggBox.setText("is Aggregate");
		add(aggBox, cc.xy(1, 3));
		add(separator2, cc.xy(1, 5));

		//======== scrollPane1 ========
		{

			//---- scriptArea ----
			scriptArea.setWrapStyleWord(true);
			scrollPane1.setViewportView(scriptArea);
		}
		add(scrollPane1, cc.xy(1, 7));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner non-commercial license
	private JComponent separator1;
	private JCheckBox aggBox;
	private JComponent separator2;
	private JScrollPane scrollPane1;
	private JTextArea scriptArea;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
