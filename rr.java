public class SettingLoader {
    private static SettingLoader settingLoader;
    private SettingLoader(){}
    public static SettingLoader getInstance(){
        if (settingLoader == null) settingLoader = new SettingLoader();
        return settingLoader;
    }

    public Setting loadSetting(String address) throws FileNotFoundException, XMLStreamException {
        FileInputStream xmlFile = new FileInputStream(address);
        return parseSettingXML(xmlFile);
    }

    private Setting parseSettingXML(FileInputStream fileInputStream) throws XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(fileInputStream);
        Setting setting = new Setting();
        while (xmlEventReader.hasNext()){
            XMLEvent xmlEvent = xmlEventReader.nextEvent();
            if (xmlEvent.isStartElement()){
                StartElement startElement = xmlEvent.asStartElement();
                switch (startElement.getName().getLocalPart()) {
                    case "title":
                        xmlEvent = xmlEventReader.nextEvent();
                        setting.setTITLE(xmlEvent.asCharacters().getData());
                        break;
                    case "window_size":
                        xmlEventReader.nextTag();
                        xmlEvent = xmlEventReader.nextEvent();
                        setting.setSTANDARD_STATES_WIDTH(Integer.parseInt(xmlEvent.asCharacters().getData()));
                        xmlEventReader.nextTag();
                        xmlEventReader.nextTag();
                        xmlEvent = xmlEventReader.nextEvent();
                        setting.setSTANDARD_STATES_HEIGHT(Integer.parseInt(xmlEvent.asCharacters().getData()));
                        break;
                    case "color":
                        xmlEventReader.nextTag();
                        xmlEvent = xmlEventReader.nextEvent();
                        String name = xmlEvent.asCharacters().getData();
                        xmlEventReader.nextTag();
                        xmlEventReader.nextTag();
                        xmlEvent = xmlEventReader.nextEvent();
                        String s = xmlEvent.asCharacters().getData();
                        Color color = new Color(Integer.parseInt(s.substring(2), 16));
                        setting.setColor(name, color);
                        break;
                }
            }
        }
        return setting;
    }
}