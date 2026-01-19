package day.vitayuzu.tabout

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.IdeActions
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.actionSystem.EditorActionManager

class Tabout : AnAction() {
    private val targets = charArrayOf(')', '}', ']', '(', '[', '{', '"', '\'', '<', '>')

    override fun actionPerformed(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR) ?: return
        val caret = editor.caretModel.currentCaret
        val document = editor.document
        val currentOffset = caret.offset

        val targetOffset =
            if (e.inputEvent?.isShiftDown == true) {
                findTabInOffset(document, currentOffset)
            } else {
                findTaboutOffset(document, currentOffset)
            }

        if (targetOffset != null) {
            caret.moveToOffset(targetOffset)
        } else {
            val actionHandler = EditorActionManager.getInstance().getActionHandler(IdeActions.ACTION_EDITOR_TAB)
            WriteCommandAction.runWriteCommandAction(editor.project) {
                actionHandler.execute(editor, caret, e.dataContext)
            }
        }
    }

    private fun findTaboutOffset(document: Document, offset: Int): Int? {
        val lineNumber = document.getLineNumber(offset)
        val endOffset = document.getLineEndOffset(lineNumber)
        for (i in offset until endOffset) {
            if (targets.contains(document.charsSequence[i])) {
                return i + 1
            }
        }
        return null
    }

    private fun findTabInOffset(document: Document, offset: Int): Int? {
        val lineNumber = document.getLineNumber(offset)
        val startOffset = document.getLineStartOffset(lineNumber)
        for (i in offset - 1 downTo startOffset) {
            if (targets.contains(document.charsSequence[i])) {
                return i
            }
        }
        return null
    }
}
