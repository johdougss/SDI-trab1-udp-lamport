package sdi.lamport;

/**
 *
 * @author Johnathan Douglas S. Santos
 */
public class Lamport {

    private String separator;
    private int Li;
    private byte[] in;
    private byte[] out;

    //<editor-fold defaultstate="collapsed" desc="CONSTRUTOR">
    /**
     *
     * @param Li Valor do relogio logico inicial
     * @param separator expressao inclusa no envio para separa time "expressa"
     * mensage
     */
    public Lamport(int Li, String separator) {
        this(Li, separator, new byte[1024], new byte[1024]);
    }

    public Lamport(int Li, String separator, byte[] in, byte[] out) {
        this.out = out;
        this.in = in;
        this.Li = Li;
        this.separator = separator;
    }

    public Lamport(byte[] in, byte[] out) {
        this(0, "::", in, out);
    }

    public Lamport() {
        this(0, "::", new byte[1024], new byte[1024]);
    }
    //</editor-fold>

    /**
     * PINGGYBACKING junta o tempo (Li) com a mensagem
     *
     * @param mensagem
     * @return (time,msg);
     */
    public synchronized byte[] send(String mensagem) {
        Li++;
        out = (Li + separator + mensagem).getBytes();
        return out;
    }

    /**
     *
     * @param msgReceive
     * @param Lj - relogio logico do processo
     * @return Mensagem
     */
    public String receive(byte[] data) {
        in = data;
        String result = new String(in);
        String[] split = result.split(separator);
        if (split.length > 1) {
            synchronized (this) {
                this.Li = (Math.max(this.Li, Integer.parseInt(split[0]))); //max(Lj,t) - Lj relogio logico desse processo
                this.Li++;
            }
            return split[1];
        } else
            return result;
    }

    //<editor-fold defaultstate="collapsed" desc="GET/SET">
    public int getLi() {
        return Li;
    }

    public byte[] getIn() {
        return in;
    }

    public byte[] getOut() {
        return out;
    }
    //</editor-fold>
}
