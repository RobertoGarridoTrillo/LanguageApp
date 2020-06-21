package LanguageApp.model;

import java.util.Objects;


/**
 *
 * @author Roberto Garrido Trillo
 */
public class Initial {

    private String directory;
    private String lastDirectory;
    private String lastFile;


    public Initial () {
    }


    /**
     *
     * @param directory The root directory
     * @param lastDirectory The last open directory
     */
    public Initial (String directory, String lastDirectory) {
        this.directory = directory;
        this.lastDirectory = lastDirectory;
    }


    public String getDirectory () {
        return directory;
    }


    public void setDirectory (String directory) {
        this.directory = directory;
    }


    public String getLastDirectory () {
        return lastDirectory;
    }


    public void setLastDirectory (String lastDirectory) {
        this.lastDirectory = lastDirectory;
    }


    public String getLastFile () {
        return lastFile;
    }


    public void setLastFile (String lastFile) {
        this.lastFile = lastFile;
    }


    @Override
    public String toString () {
        return "Initial{" + "directory=" + directory + ", lastDirectory=" +
                lastDirectory + ", lastFile=" + lastFile + '}';
    }


    @Override
    public int hashCode () {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.directory);
        hash = 89 * hash + Objects.hashCode(this.lastDirectory);
        hash = 89 * hash + Objects.hashCode(this.lastFile);
        return hash;
    }


    @Override
    public boolean equals (Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Initial other = (Initial) obj;
        if (!Objects.equals(this.directory, other.directory)) {
            return false;
        }
        if (!Objects.equals(this.lastDirectory, other.lastDirectory)) {
            return false;
        }
        if (!Objects.equals(this.lastFile, other.lastFile)) {
            return false;
        }
        return true;
    }


}

