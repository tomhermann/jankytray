package com.zombietank.jankytray;

import javax.inject.Inject;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.springframework.stereotype.Component;

import com.zombietank.support.IntegerBuilder;
import com.zombietank.support.URLBuilder;

@Component
public class ConfigurationDialog extends TitleAreaDialog {
	private Text jenkinsUrlText;
	private Text pollingIntervalText;
	private JankyOptions options;
	
	@Inject
	public ConfigurationDialog(Shell parentShell, JankyOptions options) {
		super(parentShell);
		this.options = options;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("JankyTray Options");
		newShell.setSize(500, 210);
	}	
	
	@Override
	public void create() {
		super.create();
		setTitle("JankyTray Options");
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		parent.setLayout(layout);
		
		Label label2 = new Label(parent, SWT.NONE);
		label2.setText("Jenkins URL");

		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		jenkinsUrlText = new Text(parent, SWT.BORDER);
		jenkinsUrlText.setLayoutData(gridData);
		jenkinsUrlText.setText(options.getJenkinsUrl().toExternalForm());

		// The text fields will grow with the size of the dialog
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;

		Label label1 = new Label(parent, SWT.NONE);
		label1.setText("Polling Interval");

		pollingIntervalText = new Text(parent, SWT.BORDER);
		pollingIntervalText.setLayoutData(gridData);
		pollingIntervalText.setText(Integer.toString(options.getPollingInterval()));
		
		parent.pack();
		return parent;
	}
	private boolean isValidInput() {
		boolean valid = true;
		
		if (!new IntegerBuilder(pollingIntervalText.getText()).isValidInteger()) {
			setErrorMessage("Please provide a valid polling interval.");
			valid = false;
		}
		
		if (!new URLBuilder(jenkinsUrlText.getText()).isValidUrl()) {
			setErrorMessage("Please provide a valid jenkins url.");
			valid = false;
		}
		return valid;
	}

	@Override
	protected void okPressed() {
		if(isValidInput()) {
			saveInput();
			super.okPressed();
		}
	}
	
	private void saveInput() {
		options.setJenkinsUrl(new URLBuilder(jenkinsUrlText.getText()).build());
		options.setPollingInterval(new IntegerBuilder(pollingIntervalText.getText()).build());
	}
}