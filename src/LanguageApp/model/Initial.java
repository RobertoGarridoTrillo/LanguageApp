package LanguageApp.model;

import java.util.Objects;


/**
 *
 * @author Roberto Garrido Trillo
 */
public class Initial {

    private String path;
    private String lastDirectory;
    private String lastFile;


    public Initial () {
    }


    /**
     *
     * @param directory The root path
     * @param lastDirectory The last open path
     */
    public Initial (String directory, String lastDirectory) {
        this.path = directory;
        this.lastDirectory = lastDirectory;
    }


    public String getPath () {
        return path;
    }


    public void setPath (String path) {
        this.path = path;
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
        return "Initial{" + "directory=" + path + ", lastDirectory=" +
                lastDirectory + ", lastFile=" + lastFile + '}';
    }


    @Override
    public int hashCode () {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.path);
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
        if (!Objects.equals(this.path, other.path)) {
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

