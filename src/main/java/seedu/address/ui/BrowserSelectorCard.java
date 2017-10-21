package seedu.address.ui;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

public class BrowserSelectorCard extends UiPart<Region> {

    private static final String FXML = "BrowserSelectorCard.fxml";

    @FXML
    private ImageView browserCardImage;

    public BrowserSelectorCard(String imageName) {
        super(FXML);
        fillImage(imageName);
    }

    private void fillImage(String imageName) {
        if (imageName.equals("linkedin")) {
            browserCardImage.setImage(new Image("/images/linkedin.png"));
        } else if (imageName.equals("facebook")) {
            browserCardImage.setImage(new Image("/images/facebook.png"));
        } else if (imageName.equals("meeting")) {
            browserCardImage.setImage(new Image("/images/meeting.png"));
        }
    }
}