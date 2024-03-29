package com.udacity.security.service.application;


import com.udacity.image.service.AwsImageService;
import com.udacity.image.service.ImageService;
import com.udacity.security.service.data.PretendDatabaseSecurityRepositoryImpl;
import com.udacity.security.service.data.SecurityRepository;
import com.udacity.security.service.service.SecurityService;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * This is the primary JFrame for the application that contains all the top-level JPanels.
 * <p>
 * We're not using any dependency injection framework, so this class also handles constructing
 * all our dependencies and providing them to other classes as necessary.
 */
public class CatpointGui extends JFrame {
    private transient SecurityRepository securityRepository = new PretendDatabaseSecurityRepositoryImpl();

    private transient ImageService imageService = new AwsImageService();

    private SecurityService securityService = new SecurityService(securityRepository, imageService);

    private DisplayPanel displayPanel = new DisplayPanel(securityService);

    private ControlPanel controlPanel = new ControlPanel(securityService);

    private SensorPanel sensorPanel = new SensorPanel(securityService);

    private ImagePanel imagePanel = new ImagePanel(securityService);

    public CatpointGui() {
        setLocation(100, 100);
        setSize(600, 850);
        setTitle("Very Secure App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout());
        mainPanel.add(displayPanel, "wrap");
        mainPanel.add(imagePanel, "wrap");
        mainPanel.add(controlPanel, "wrap");
        mainPanel.add(sensorPanel);

        getContentPane().add(mainPanel);
    }
}
