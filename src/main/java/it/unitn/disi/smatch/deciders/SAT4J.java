package it.unitn.disi.smatch.deciders;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.LecteurDimacs;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * SAT4J-based Solver.
 *
 * @author Mikalai Yatskevich mikalai.yatskevich@comlab.ox.ac.uk
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class SAT4J implements ISATSolver {

    public boolean isSatisfiable(String input) throws SATSolverException {
        boolean result;
        try {
            ISolver solver = SolverFactory.newDefault();
            solver.setTimeout(3600); // 1 hour timeout
            Reader reader = new LecteurDimacs(solver);
            IProblem problem = reader.parseInstance(new ByteArrayInputStream(input.getBytes()));
            result = problem.isSatisfiable();
        } catch (ParseFormatException | TimeoutException | IOException e) {
            throw new SATSolverException(e.getClass().getSimpleName() + ": " + e.getMessage() + " on input: " + input, e);
        } catch (ContradictionException e) {
            result = false;
        }
        return result;
    }
}