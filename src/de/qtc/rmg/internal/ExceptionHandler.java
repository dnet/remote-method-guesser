package de.qtc.rmg.internal;

import de.qtc.rmg.io.Logger;
import de.qtc.rmg.utils.RMGUtils;

public class ExceptionHandler {

    public static void internalError(String functionName, String message)
    {
        Logger.printMixedYellow("Internal error within the", functionName, "function.");
        Logger.println(message);
        RMGUtils.exit();
    }

    public static void internalException(Exception e, String functionName, boolean exit)
    {
        Logger.printMixedYellow("Internal error. Caught unexpected", e.getClass().getName(), "within the ");
        Logger.printlnPlainMixedBlue(functionName, "function.");
        RMGUtils.stackTrace(e);

        if(exit)
            RMGUtils.exit();
    }

    public static void unexpectedException(Exception e, String during1, String during2, boolean exit)
    {
        Logger.printMixedYellow("Caught unexpected", e.getClass().getName(), "during ");
        Logger.printlnPlainMixedBlueFirst(during1, during2 + ".");
        Logger.eprintln("Please report this to improve rmg :)");
        RMGUtils.stackTrace(e);

        if(exit)
            RMGUtils.exit();
    }

    public static void alreadyBoundException(Exception e, String boundName)
    {
        Logger.eprintlnMixedYellow("Bind operation", "was accepted", "by the server.");
        Logger.eprintlnMixedBlue("But the boundname", boundName, "is already bound.");
        Logger.eprintlnMixedYellow("Use the", "rebind", "action instead.");
    }

    public static void nonLocalhost(Exception e, String callName, boolean bypass)
    {
        Logger.eprintlnMixedYellow("Registry", "rejected " + callName + " call", "because it was not send from localhost.");

        if(!bypass)
            Logger.eprintlnMixedBlue("You can attempt to bypass this restriction using the", "--localhost-bypass", "option.");
        else
            Logger.eprintlnMixedBlue("Localhost bypass was used but", "failed.");

        RMGUtils.showStackTrace(e);
    }

    public static void jep290(Exception e)
    {
        Logger.eprintMixedYellow("RMI registry", "rejected", "deserialization of the supplied gadget");
        Logger.printlnPlainYellow(" (JEP290 is installed).");
        RMGUtils.showStackTrace(e);
    }

    public static void deserializeClassNotFound(Exception e)
    {
        Logger.eprintlnMixedYellow("Server", "accepted", "deserialization of the supplied gadget, but");
        Logger.eprintlnMixedBlue("during the deserialization, a", "ClassNotFoundException", "was encountered.");
        Logger.eprintMixedYellow("The supplied gadget may have", "worked anyway", "or it is ");
        Logger.printlnPlainMixedBlueFirst("not available", "on the servers classpath.", "");
        RMGUtils.showStackTrace(e);
    }

    public static void deserializeClassNotFoundRandom(Exception e, String during1, String during2, String className)
    {
        Logger.printlnMixedYellow("Caught", "ClassNotFoundException", "during " + during1 + " " + during2 + ".");
        Logger.printlnMixedBlue("Server attempted to deserialize dummy class", className + ".");
        Logger.printlnMixedYellow("Deserialization attack", "probably worked :)");
        RMGUtils.showStackTrace(e);
    }

    public static void deserlializeClassCast(Exception e, boolean wasString)
    {
        Logger.printlnMixedYellow("Caught", "ClassCastException", "during deserialization attack.");

        if(wasString)
            Logger.printlnMixedBlue("The server uses either", "readString()", "to unmarshal String parameters, or");

        Logger.printlnMixedYellowFirst("Deserialization attack", "was probably successful :)");
        RMGUtils.showStackTrace(e);
    }

    public static void codebaseClassNotFound(Exception e, String className)
    {
        Logger.eprintlnMixedYellow("Caught", "ClassNotFoundException", "during codebase attack.");
        Logger.eprintlnMixedBlue("The payload class could", "not be loaded", "from the specified endpoint.");
        Logger.eprintMixedYellow("The endpoint is probably configured with", "useCodeBaseOnly=true");
        Logger.printlnPlainYellow(" (not vulnerable)");
        Logger.eprintlnMixedBlue("or the file", className + ".class", "was not found on the specified endpoint.");
        RMGUtils.showStackTrace(e);
    }

    public static void codebaseSecurityManager(Exception e)
    {
        Logger.eprintlnMixedYellow("The class loader of the specified target is", "disabled.");
        RMGUtils.showStackTrace(e);
    }

    public static void codebaseClassNotFoundRandom(Exception e, String className, String payloadName)
    {
        Logger.printlnMixedBlue("Remote class loader attempted to load dummy class", className);
        Logger.printlnMixedYellow("Codebase attack", "probably worked :)");

        Logger.println("");
        Logger.eprintlnMixedYellow("If where was no callback, the server did not load the attack class", payloadName + ".class.");
        Logger.eprintln("The class is probably known by the server or it was already loaded before.");
        Logger.eprintlnMixedBlue("In this case, you should try a", "different classname.");
        RMGUtils.showStackTrace(e);
    }

    public static void codebaseClassCast(Exception e, boolean wasString)
    {
        Logger.printlnMixedYellow("Caught", "ClassCastException", "during codebase attack.");

        if(wasString)
            Logger.printlnMixedBlue("The server uses either", "readString()", "to unmarshal String parameters, or");

        Logger.printlnMixedYellowFirst("Codebase attack", "most likely", "worked :)");
        RMGUtils.showStackTrace(e);
    }

    public static void connectionRefused(Exception e, String during1, String during2)
    {
        Logger.eprintlnMixedYellow("Caught unexpected", "ConnectException", "during " + during1 + " " + during2 + ".");
        Logger.eprintMixedBlue("Target", "refused", "the connection.");
        Logger.printlnPlainMixedBlue(" The specified port is probably", "closed.");
        RMGUtils.showStackTrace(e);
        RMGUtils.exit();
    }

    public static void noRouteToHost(Exception e, String during1, String during2)
    {
        Logger.eprintlnMixedYellow("Caught unexpected", "NoRouteToHostException", "during " + during1 + " " + during2 + ".");
        Logger.eprintln("Have you entered the correct target?");
        RMGUtils.showStackTrace(e);
        RMGUtils.exit();
    }

    public static void noJRMPServer(Exception e, String during1, String during2)
    {
        Logger.eprintlnMixedYellow("Caught unexpected", "ConnectIOException", "during " + during1 + " " + during2 + ".");
        Logger.eprintMixedBlue("Remote endpoint is either", "no RMI endpoint", "or uses an");
        Logger.printlnPlainBlue(" SSL socket.");
        Logger.eprintlnMixedYellow("Retry the operation using the", "--ssl", "option.");
        RMGUtils.showStackTrace(e);
        RMGUtils.exit();
    }

    public static void sslError(Exception e, String during1, String during2)
    {
        Logger.eprintlnMixedYellow("Caught unexpected", "SSLException", "during " + during1 + " " + during2 + ".");
        Logger.eprintlnMixedBlue("You probably used", "--ssl", "on a plaintext connection?");
        RMGUtils.showStackTrace(e);
        RMGUtils.exit();
    }

    public static void invalidClass(Exception e, String endpoint, String className)
    {
        Logger.eprintMixedYellow(endpoint, "rejected", "deserialization of class ");
        Logger.printPlainBlue(className);
        Logger.printlnPlainYellow(" (JEP290 is installed).");
    }

    public static void accessControl(Exception e, String during1, String during2)
    {
        Logger.printlnMixedYellow("Caught unexpected", "AccessControlException", "during " + during1 + " " + during2 + ".");
        Logger.printlnMixedBlue("The servers", "SecurityManager", "may refused the operation.");
        RMGUtils.showStackTrace(e);
    }

    public static void singleEntryRegistry(Exception e, String during1)
    {
        Logger.printlnMixedYellow("- Caught", "AccessException", "during " + during1 + "call.");
        Logger.printlnMixedBlue("  --> The servers seems to use a", "SingleEntryRegistry", "(probably JMX based).");
        Logger.statusUndecided("Vulnerability");
        RMGUtils.showStackTrace(e);
    }

    public static void eofException(Exception e, String during1, String during2)
    {
        Logger.printlnMixedYellow("Caught unexpected", "EOFException", "during " + during1 + " " + during2 + ".");
        Logger.eprintlnMixedBlue("You probably used", "--ssl", "on a plain TCP port?");
        RMGUtils.showStackTrace(e);
        RMGUtils.exit();
    }

    public static void invalidListenerFormat(boolean gadget)
    {
        if(gadget)
            Logger.printlnMixedBlue("Selected gadget expects a", "listener", "as command input.");

        Logger.eprintlnMixedYellow("Listener must be specified in", "host:port", "format.");
        RMGUtils.exit();
    }

    public static void invalidSignature(String signature)
    {
        Logger.eprintlnMixedYellow("Encountered invalid function signature:", signature);
        Logger.eprintln("Correct the format and try again :)");
        RMGUtils.exit();
    }

    public static void unknownDeserializationException(Exception e)
    {
        Throwable cause = RMGUtils.getCause(e);

        Logger.printlnMixedYellow("Caught", cause.getClass().getName(), "during deserialization attack.");
        Logger.eprintlnMixedBlue("This could be caused by your gadget an the attack", "probably worked anyway.");
        Logger.eprintlnMixedYellow("If it did not work, you can retry with", "--stack-trace", "to see the details.");
        RMGUtils.showStackTrace(e);
    }

    public static void unsupportedClassVersion(Exception e, String during1, String during2)
    {
        Logger.eprintlnMixedYellow("Caught", e.getClass().getName(), "during " + during1 + " " + during2 + ".");
        Logger.eprintlnMixedBlue("You probably used an", "incompatible compiler version", "for class generation.");
        Logger.eprintln("Exception Message: " + e.getMessage());
        RMGUtils.showStackTrace(e);
    }
}