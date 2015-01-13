package org.uml.visual.dialogs;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import org.netbeans.api.visual.widget.LabelWidget;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.uml.model.relations.CardinalityEnum;
import org.uml.model.relations.HasBaseRelation;
import org.uml.model.relations.RelationBase;
import org.uml.model.relations.UseRelation;

/**
 *
 * @author Boris
 */
public class CardinalityChangePanel extends javax.swing.JPanel {

    private DialogDescriptor dd;
    private Dialog dialog;
    private final JButton btnOK = new JButton("OK");
    private final JButton btnCancel = new JButton("Cancel");
    private RelationBase relation;
    private LabelWidget cardinalityLabel;
    private boolean source;

    public CardinalityChangePanel(RelationBase relationB, boolean src, LabelWidget cardinalityLbl) {
        initComponents();
        relation = relationB;
        cardinalityLabel = cardinalityLbl;
        source = src;

        fillCardinalityComboBox(cbxCardinality);
        if (relation instanceof HasBaseRelation) {
            if (source) {
                cbxCardinality.setSelectedItem(((HasBaseRelation) relation).getCardinalitySource());
            } else {
                cbxCardinality.setSelectedItem(((HasBaseRelation) relation).getCardinalityTarget());
            }
        } else if (relation instanceof UseRelation) {
            if (source) {
                cbxCardinality.setSelectedItem(((UseRelation) relation).getCardinalitySource());
            } else {
                cbxCardinality.setSelectedItem(((UseRelation) relation).getCardinalityTarget());
            }
        }

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardinalityEnum card = (CardinalityEnum) cbxCardinality.getSelectedItem();
                if (relation instanceof HasBaseRelation) {
                    if (source) {
                        ((HasBaseRelation) relation).setCardinalitySource(card);
                    } else {
                        ((HasBaseRelation) relation).setCardinalityTarget(card);
                    }
                } else if (relation instanceof UseRelation) {
                    if (source) {
                        ((UseRelation) relation).setCardinalitySource(card);
                    } else {
                        ((UseRelation) relation).setCardinalityTarget(card);
                    }
                }
                cardinalityLabel.setLabel(card.toString());
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
    }

    private void fillCardinalityComboBox(JComboBox<CardinalityEnum> comboBox) {
        comboBox.addItem(CardinalityEnum.One2One);
        comboBox.addItem(CardinalityEnum.One2Many);
        comboBox.addItem(CardinalityEnum.Zero2One);
        comboBox.addItem(CardinalityEnum.Zero2Many);
        comboBox.setRenderer(new CardinalityListCellRenderer());
        comboBox.setEnabled(true);
    }

    private class CardinalityListCellRenderer implements ListCellRenderer<Object> {

        @Override
        public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel renderer = (JLabel) new DefaultListCellRenderer().getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof CardinalityEnum) {
                renderer.setText(value + " (" + ((CardinalityEnum) value).name().replace("2", " to ") + ")");
            }
            return renderer;
        }
    }

    public void openRelationDialog() {
        dd = new DialogDescriptor(this, "Change cardinality", true, new Object[]{btnOK, btnCancel}, null, DialogDescriptor.DEFAULT_ALIGN, null, null);
        dialog = DialogDisplayer.getDefault().createDialog(dd);
        dialog.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblCardinality = new javax.swing.JLabel();
        cbxCardinality = new javax.swing.JComboBox<CardinalityEnum>();

        org.openide.awt.Mnemonics.setLocalizedText(lblCardinality, org.openide.util.NbBundle.getMessage(CardinalityChangePanel.class, "CardinalityChangePanel.lblCardinality.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCardinality)
                .addGap(18, 18, 18)
                .addComponent(cbxCardinality, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCardinality)
                    .addComponent(cbxCardinality, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<CardinalityEnum> cbxCardinality;
    private javax.swing.JLabel lblCardinality;
    // End of variables declaration//GEN-END:variables
}
