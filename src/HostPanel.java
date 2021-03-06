
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import org.apache.commons.io.FileUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex
 */
public class HostPanel extends javax.swing.JPanel {
    private final Collection<TransferableFile> fileCollection;
    private final MainFrame mainFrame;
    private SendTransferableFiles transfer;
    
    /**
     * Creates new form HostPanel
     */
    public HostPanel(MainFrame main) {
        mainFrame = main;
        fileCollection = new LinkedList<>();
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dropTargetScroll = new javax.swing.JScrollPane();
        dropTargetArea = new javax.swing.JTextArea();
        dropTargetTitle = new javax.swing.JLabel();
        globalProgress = new javax.swing.JProgressBar();
        currentProgress = new javax.swing.JProgressBar();
        transferButton = new javax.swing.JButton();
        dropTargetValue = new javax.swing.JLabel();
        dropTargetClear = new javax.swing.JLabel();
        statusDisplay1 = new javax.swing.JLabel();
        statusDisplay2 = new javax.swing.JLabel();

        setFocusable(false);

        dropTargetScroll.setFocusable(false);

        dropTargetArea.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                if(transfer == null || transfer.isDone()) {
                    evt.acceptDrop(DnDConstants.ACTION_LINK);
                    Transferable transferable = evt.getTransferable();

                    try {
                        if(transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                            ProcessDropTarget pdt =
                            new ProcessDropTarget((List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor));
                            pdt.execute();
                        }
                    } catch(UnsupportedFlavorException | IOException ex) {
                        Logger.getLogger(HostPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        dropTargetArea.setEditable(false);
        dropTargetArea.setBackground(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        dropTargetArea.setFocusable(false);
        dropTargetScroll.setViewportView(dropTargetArea);

        dropTargetTitle.setText("Drag files to host onto box below");
        dropTargetTitle.setFocusable(false);

        globalProgress.setFocusable(false);
        globalProgress.setRequestFocusEnabled(false);
        globalProgress.setStringPainted(true);

        currentProgress.setFocusable(false);
        currentProgress.setRequestFocusEnabled(false);
        currentProgress.setStringPainted(true);

        transferButton.setText("Host");
        transferButton.setActionCommand("host");
        transferButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transferButtonActionPerformed(evt);
            }
        });

        dropTargetValue.setText("Files to Share: 0");
        dropTargetValue.setFocusable(false);

        dropTargetClear.setText("Clear");
        dropTargetClear.setFocusable(false);
        dropTargetClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dropTargetClearMouseReleased(evt);
            }
        });

        statusDisplay1.setText("");

        statusDisplay2.setText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dropTargetScroll)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(globalProgress, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                            .addComponent(currentProgress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(statusDisplay1, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                            .addComponent(statusDisplay2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(transferButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dropTargetTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dropTargetValue, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(dropTargetClear)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dropTargetTitle)
                    .addComponent(dropTargetValue)
                    .addComponent(dropTargetClear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dropTargetScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(currentProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(statusDisplay1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(globalProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(statusDisplay2)))
                    .addComponent(transferButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void dropTargetClearMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dropTargetClearMouseReleased
        if(transfer == null || transfer.isDone()) {
            dropTargetArea.setText("");
            fileCollection.clear();
            dropTargetValue.setText("Files to Share: 0");
        }
    }//GEN-LAST:event_dropTargetClearMouseReleased

    private void transferButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transferButtonActionPerformed
        switch(evt.getActionCommand()) {
            case "host":
                mainFrame.jTabbedPane1.setEnabledAt(1, false);
                dropTargetClear.setEnabled(false);
                transferButton.setText("Cancel");
                transferButton.setActionCommand("cancel");
                
                try {
                    int port = (int)((Math.random() * 8999) + 1000);
                    mainFrame.setTitle("JavaP2P - Listening on: " + InetAddress.getLocalHost().getHostAddress() + ":" + port);
                    
                    transfer = new SendTransferableFiles(port);
                    transfer.execute();
                } catch (UnknownHostException ex) {
                    Logger.getLogger(HostPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
                
            case "cancel":
                if(transfer != null)
                    transfer.cancel(true);
                break;
        }
    }//GEN-LAST:event_transferButtonActionPerformed
    
    private class ProcessDropTarget extends SwingWorker<Void, String> {
        private final List<File> transferData;
        
        public ProcessDropTarget(List<File> transferData) {
            this.transferData = transferData;
        }
        
        @Override
        protected Void doInBackground() throws Exception {
            dropTargetClear.setEnabled(false);
            transferButton.setEnabled(false);
            
            for(File data : transferData) {
                File path = data.getParentFile();
                
                if(data.isDirectory()) {
                    for(File file : FileUtils.listFiles(data, null, true)) {
                        fileCollection.add(new TransferableFile(file, path));
                        this.publish(file.toString() + "\n");
                    }
                } else {
                    fileCollection.add(new TransferableFile(data, path));
                    this.publish(data.toString() + "\n");
                }
            }
            
            return null;
        }
        
        @Override
        protected void process(List<String> chunks) {
            for(String chunk : chunks)
                dropTargetArea.append(chunk);
        }
        
        @Override
        protected void done() {
            dropTargetClear.setEnabled(true);
            transferButton.setEnabled(true);
            dropTargetValue.setText("Files to Share: " + fileCollection.size());
        }
    }
    
    private class SendTransferableFiles extends SwingWorker<Boolean, String> {
        private final int port;
        
        public SendTransferableFiles(int port) {
            this.port = port;
            this.addPropertyChangeListener(new PropertyChangeListener(){
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    if ("progress".equals(evt.getPropertyName()))
                        currentProgress.setValue((Integer) evt.getNewValue());
                }
            });
        }
        
        @Override
        protected Boolean doInBackground() throws Exception {
            try (
                    ServerSocket server = new ServerSocket(port);
                    Socket socket = server.accept(); 
                    BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
                    DataOutputStream dos = new DataOutputStream(bos)
                ) {
                dos.writeInt(fileCollection.size());
                
                int prog = 0;
                mainFrame.setTitle("JavaP2P - Sending data...");
                for(TransferableFile transferableFile : fileCollection) {
                    globalProgress.setValue(calcPercent(++prog, fileCollection.size()));
                    
                    String name = transferableFile.file.getName();
                    String suffix = " (" + humanReadableByteCount(transferableFile.file.length(), false) + ")";
                    int truncatePoint = truncatePoint(
                        statusDisplay1.getFontMetrics(new Font("Segoe UI", Font.PLAIN, 11)),
                        name, suffix,
                        statusDisplay1.getBounds().width
                    );
                    statusDisplay1.setText(name.substring(0, truncatePoint) + suffix);
                    statusDisplay2.setText(prog + " of " + fileCollection.size());
                    
                    String relativeFile = transferableFile.file.getAbsolutePath().substring(transferableFile.parent.getAbsolutePath().length());
                    dos.writeUTF(relativeFile);
                    dos.writeLong(transferableFile.file.length());
                    
                    try (
                            FileInputStream fis = new FileInputStream(transferableFile.file);
                            BufferedInputStream bis = new BufferedInputStream(fis)
                        ) {
                        long bp = 0, fileLength = transferableFile.file.length();
                        byte[] bb = new byte[socket.getSendBufferSize()];
                        int br = 0;
                        
                        while(!this.isCancelled() && (br = bis.read(bb)) != -1) {
                            dos.write(bb, 0, br);
                            this.setProgress(calcPercent(bp += br, fileLength));
                        }
                    }
                    if(this.isCancelled())
                        return false;
                }
            }
            
            return true;
        }
        
        @Override
        protected void done() {
            try {
                this.get();
            } catch (InterruptedException | ExecutionException | CancellationException ex) {
                Logger.getLogger(HostPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            mainFrame.jTabbedPane1.setEnabledAt(1, true);
            dropTargetClear.setEnabled(true);
            transferButton.setText("Host");
            transferButton.setActionCommand("host");
            mainFrame.setTitle("JavaP2P");
        }
    }
    
    private class TransferableFile {
        public final File file;
        public final File parent;
        
        public TransferableFile(File file, File parent) {
            this.file = file;
            this.parent = parent;
        }
    }
    
    private int truncatePoint(FontMetrics fontMetrics, String string, String suffix, int boundsWidth) {
        int stringWidth = fontMetrics.stringWidth(string + suffix);
        if(stringWidth > boundsWidth)
            return truncatePoint(fontMetrics, string.substring(0, string.length() - 1), suffix, boundsWidth);
        return string.length();
    }
    
    private String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "i" : "");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
    
    private int calcPercent(long numerator, long denominator) {
        return (int)(numerator * 100.0 / denominator + 0.5);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar currentProgress;
    private javax.swing.JTextArea dropTargetArea;
    private javax.swing.JLabel dropTargetClear;
    private javax.swing.JScrollPane dropTargetScroll;
    private javax.swing.JLabel dropTargetTitle;
    private javax.swing.JLabel dropTargetValue;
    private javax.swing.JProgressBar globalProgress;
    private javax.swing.JLabel statusDisplay1;
    private javax.swing.JLabel statusDisplay2;
    private javax.swing.JButton transferButton;
    // End of variables declaration//GEN-END:variables
}
