/*
 * This file is part of git-commit-id-plugin by Konrad Malawski <konrad.malawski@java.pl>
 *
 * git-commit-id-plugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * git-commit-id-plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with git-commit-id-plugin.  If not, see <http://www.gnu.org/licenses/>.
 */

package pl.project13.jgit;

import org.eclipse.jgit.lib.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class DescribeResultTest {

  @NotNull
  String VERSION = "v2.5";
  @NotNull
  String DEFAULT_ABBREV_COMMIT_ID = "0000000";
  @NotNull
  String G_DEFAULT_ABBREV_COMMIT_ID = "g" + DEFAULT_ABBREV_COMMIT_ID;
  @NotNull
  String FULL_COMMIT_ID = "0000000000000000000000000000000000000000";
  @NotNull
  String G_FULL_COMMIT_ID = "g" + FULL_COMMIT_ID;
  @NotNull
  String DIRTY_MARKER = "DEV";

  @Test
  public void shouldToStringForTag() throws Exception {
    // given
    DescribeResult res = new DescribeResult(VERSION);

    // when
    String s = res.toString();

    // then
    assertThat(s).isEqualTo(VERSION);
  }


  @Test
  public void shouldToStringForDirtyTag() throws Exception {
    // given
    DescribeResult res = new DescribeResult(VERSION, 2, ObjectId.zeroId(), true, DIRTY_MARKER);

    // when
    String s = res.toString();

    // then
    assertThat(s).isEqualTo(VERSION + "-" + 2 + "-" + G_DEFAULT_ABBREV_COMMIT_ID + "-" + DIRTY_MARKER);
  }

  @Test
  public void shouldToStringForDirtyTagAnd10Abbrev() throws Exception {
    // given
    DescribeResult res = new DescribeResult(VERSION, 2, ObjectId.zeroId(), true, DIRTY_MARKER).withCommitIdAbbrev(10);
    String expectedHash = "g0000000000";
    // when
    String s = res.toString();

    // then
    assertThat(s).isEqualTo(VERSION + "-" + 2 + "-" + expectedHash + "-" + DIRTY_MARKER);
  }

  @Test
  public void shouldToStringFor2CommitsAwayFromTag() throws Exception {
    // given
    DescribeResult res = new DescribeResult(VERSION, 2, ObjectId.zeroId());

    // when
    String s = res.toString();

    // then
    assertThat(s).isEqualTo(VERSION + "-" + 2 + "-" + G_DEFAULT_ABBREV_COMMIT_ID);
  }

  @Test
  public void shouldToStringForNoTagJustACommit() throws Exception {
    // given
    DescribeResult res = new DescribeResult(ObjectId.zeroId());

    // when
    String s = res.toString();

    // then
    assertThat(s).isEqualTo(DEFAULT_ABBREV_COMMIT_ID);
  }
}
