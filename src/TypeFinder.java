import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.ListCellRenderer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;


public class TypeFinder {
	  public static void main(String[] args) throws IOException {



	    parseDirectory("/home/andrew/Projects/SENG300-Iteration1/TestFiles");

		 /*
		if (args.length == 2 ) {
		  	String directory = args[0];	
			String javaType= args[1];	//Need to use to count which java type you want
			parse(readFileToString(directory));
		}
		else {
			System.out.println("Please provide directory and java type");
		}*/

	  }
	  
	  


	  public static void parse(String str) {
		  
		  Map options = JavaCore.getOptions();
		  options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_5);
		  options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_5);
		  options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_5);


		  
  
		  
			ASTParser parser = ASTParser.newParser(AST.JLS3);
			parser.setCompilerOptions(options);
			parser.setSource(str.toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			parser.setResolveBindings(true);
			parser.setEnvironment(null, null, null, true);
			parser.setUnitName("doesThisMatter.java");
			

			
			
			 
			 
			final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
	 
			cu.accept(new ASTVisitor() {
	 

				public boolean visit(TypeDeclaration node) {
					String name = node.getName().getFullyQualifiedName();
					System.out.println("Declaration: " +name);
					
					System.out.println("This class extends " + node.getSuperclassType());
					
					ITypeBinding e = node.resolveBinding();
					
					if (e.getInterfaces() != null) {
						ITypeBinding[] interfaces = e.getInterfaces();
						for (ITypeBinding i : interfaces) System.out.println("implements Reference: " + i.getName());
					}
					

					return super.visit(node); 
				}
				

				
				
				public boolean visit(VariableDeclarationFragment node) {
					String name = node.getName().getFullyQualifiedName();
					System.out.println("Reference: " + name);
					

					return super.visit(node);
				}
				
				
				public boolean visit(SimpleName node) {
					String name = node.getFullyQualifiedName();
					IBinding bind = node.resolveBinding();
					if (bind.getKind() == IBinding.VARIABLE) {
						IVariableBinding ivb = (IVariableBinding) bind;
						if (ivb.isParameter()) {
						System.out.println("Parameter: " + name);
						}
					}
					boolean isDecl = node.isDeclaration();
					
					
					return super.visit(node);
				}
				
		
				public boolean visit(ClassInstanceCreation node) {
					Type name = node.getType();
			
					System.out.println("Reference: " + name);
					


					return false; // do not continue 
			}
				

				
				
				public boolean visit(AnnotationTypeDeclaration node) {
					String name = node.getName().getFullyQualifiedName();
					System.out.println("Declaration: " + name);
					
					return false; // do not continue 
				}
				
				
				public boolean visit(EnumDeclaration node) {
					String name = node.getName().getFullyQualifiedName();
					System.out.println("Declaration: " + name);
					
					ITypeBinding e = node.resolveBinding();
					
					if (e.getInterfaces() != null) {
						ITypeBinding[] interfaces = e.getInterfaces();
						for (ITypeBinding i : interfaces) System.out.println("implements Reference: " + i.getName());
						
					}
					
					return false; // do not continue 
				}
				


			});
	 
		}
	  
	  
	  public static String readFileToString(String filePath) throws IOException {
			StringBuilder fileData = new StringBuilder(1000);
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
	 
			char[] buf = new char[10];
			int numRead = 0;
			while ((numRead = reader.read(buf)) != -1) {
				//System.out.println(numRead);
				String readData = String.valueOf(buf, 0, numRead);
				fileData.append(readData);
				buf = new char[1024];
			}
	 
			reader.close();
	 
			return  fileData.toString(); 
	 
	}
	  
	  public static void parseDirectory(String filePath) throws IOException {
		  File directory = new File(filePath);
		  
		  File[] files = directory.listFiles();
		  
		  for (File i: files) {
			  String currentFilePath = i.getAbsolutePath();
			  if (i.isFile()) parse(readFileToString(currentFilePath));
		  }
		  
		  
	  }
	  
	  
}
